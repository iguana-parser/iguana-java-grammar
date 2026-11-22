//! The highlighting rules of Java (`iguana_runtime::highlight`). The
//! generated `lib.rs` declares this file as the module `highlighting`.

use iguana_runtime::{
    grammar::Grammar,
    highlight::Highlight,
    input::{Input, Span},
    parse_tree::{NodeKind, ParseTreeNode},
    serde::Serialize,
};

use crate::{
    grammar::JavaGrammar,
    parse_tree::{ParseTree, Token},
};

/// The highlight groups of Java.
#[derive(Debug, Clone, Copy, PartialEq, Eq, Serialize)]
#[serde(crate = "iguana_runtime::serde", rename_all = "lowercase")]
pub enum Group {
    Keyword,
    Operator,
    Comment,
    Number,
    String,
    #[serde(rename = "string.escape")]
    StringEscape,
    Type,
    Function,
    Variable,
    Decorator,
}

/// Pushes the highlights of `token` from its terminal alone, for
/// `lexical_highlight` and for each token in `rules`.
pub fn token_rules(token: Token, input: &Input, highlights: &mut Vec<Highlight<Group>>) {
    let span = token.span();
    let mut push = |span: Span, group: Group| highlights.push(Highlight::new(span, group));
    match token.kind.name() {
        "IntegerLiteral" | "FloatingPointLiteral" => push(span, Group::Number),
        "StringLiteral" | "TextBlock" | "CharacterLiteral" => {
            push(span, Group::String);
            for escape in escapes(span, input.chars(span)) {
                push(escape, Group::StringEscape);
            }
        }
        "BooleanLiteral" | "NullLiteral" | "Else" | "NonSealed" | r#""_""# | r#""abstract""#
        | r#""assert""# | r#""boolean""# | r#""break""# | r#""byte""# | r#""case""#
        | r#""catch""# | r#""char""# | r#""class""# | r#""continue""# | r#""default""#
        | r#""do""# | r#""double""# | r#""enum""# | r#""exports""# | r#""extends""#
        | r#""final""# | r#""finally""# | r#""float""# | r#""for""# | r#""if""#
        | r#""implements""# | r#""import""# | r#""instanceof""# | r#""int""# | r#""interface""#
        | r#""long""# | r#""module""# | r#""native""# | r#""new""# | r#""open""# | r#""opens""#
        | r#""package""# | r#""permits""# | r#""private""# | r#""protected""# | r#""provides""#
        | r#""public""# | r#""record""# | r#""requires""# | r#""return""# | r#""sealed""#
        | r#""short""# | r#""static""# | r#""strictfp""# | r#""super""# | r#""switch""#
        | r#""synchronized""# | r#""this""# | r#""throw""# | r#""throws""# | r#""to""#
        | r#""transient""# | r#""transitive""# | r#""try""# | r#""uses""# | r#""var""#
        | r#""void""# | r#""volatile""# | r#""when""# | r#""while""# | r#""with""#
        | r#""yield""# => push(span, Group::Keyword),
        "AssignmentOperator" | r#""!""# | r#""!=""# | r#""%""# | r#""&""# | r#""&&""#
        | r#""(""# | r#"")""# | r#""*""# | r#""+""# | r#""++""# | r#"",""# | r#""-""#
        | r#""--""# | r#""->""# | r#"".""# | r#""...""# | r#""/""# | r#"":""# | r#""::""#
        | r#"";""# | r#""<""# | r#""<<""# | r#""<=""# | r#""=""# | r#""==""# | r#"">""#
        | r#"">=""# | r#"">>""# | r#"">>>""# | r#""?""# | r#""@""# | r#""[""# | r#""]""#
        | r#""^""# | r#""{""# | r#""|""# | r#""||""# | r#""}""# | r#""~""# => {
            push(span, Group::Operator)
        }
        "Comment" => push(span, Group::Comment),
        _ => {}
    }
}

/// Pushes the highlights of `node` of a parse tree.
pub fn rules(node: ParseTree<'_>, input: &Input, highlights: &mut Vec<Highlight<Group>>) {
    if let ParseTree::Token(token) = node {
        token_rules(token, input, highlights);
    }
    let span = node.span();
    let mut push = |span: Span, group: Group| highlights.push(Highlight::new(span, group));
    match node.display_name() {
        // `TypeIdentifier` and `UnqualifiedMethodIdentifier` appear in the
        // grammar only where a type or a method name is expected. The scanner
        // matches both as `Identifier`, which has no group.
        "TypeIdentifier" => push(span, Group::Type),
        "UnqualifiedMethodIdentifier" => push(span, Group::Function),
        "MethodDeclarator" => push_first(&mut push, node, "Identifier", Group::Function),
        "ClassTypeToInstantiate" => {
            // The class's names, without the annotations between them.
            for symbol in symbols(node) {
                if is_token(&symbol, "Identifier") {
                    push(symbol.span(), Group::Type);
                }
            }
        }
        "VariableDeclaratorId" | "ConciseLambdaParameter" | "RecordComponent" | "EnumConstant" => {
            push_first(&mut push, node, "Identifier", Group::Variable)
        }
        "SuperSuffix" => {
            let group = if symbols(node)
                .iter()
                .any(|symbol| symbol.display_name() == "Arguments")
            {
                Group::Function
            } else {
                Group::Variable
            };
            push_first(&mut push, node, "Identifier", group);
        }
        "Expression" => {
            // A method reference: the name after `::`.
            let symbols = symbols(node);
            if let Some(colons) = symbols.iter().position(|symbol| is_token(symbol, "\"::\""))
                && let Some(name) = symbols[colons..]
                    .iter()
                    .find(|symbol| is_token(*symbol, "Identifier"))
            {
                push(name.span(), Group::Function);
            }
        }
        "Selector" => {
            let symbols = symbols(node);
            let group = if symbols
                .first()
                .is_some_and(|first| is_token(first, "\"new\""))
            {
                Group::Type
            } else if symbols
                .iter()
                .any(|symbol| symbol.display_name() == "Arguments")
            {
                Group::Function
            } else {
                Group::Variable
            };
            push_first(&mut push, node, "Identifier", group);
        }
        "Annotation" => {
            // The tokens of the `@` and the name, without the arguments. A
            // qualified name's dots and `TypeIdentifier` are shorter than the
            // name, and their own groups would win over the name's.
            for symbol in symbols(node).into_iter().take(2) {
                for token in tokens(symbol) {
                    push(token.span(), Group::Decorator);
                }
            }
        }
        _ => {}
    }
}

/// Pushes the first of `node`'s symbols that is a token of `terminal` as
/// `group`.
fn push_first(
    push: &mut impl FnMut(Span, Group),
    node: ParseTree<'_>,
    terminal: &str,
    group: Group,
) {
    if let Some(symbol) = symbols(node)
        .iter()
        .find(|symbol| is_token(*symbol, terminal))
    {
        push(symbol.span(), group);
    }
}

/// Whether `node` is a token of the terminal named `terminal`.
fn is_token(node: &ParseTree<'_>, terminal: &str) -> bool {
    node.kind() == NodeKind::Token && node.display_name() == terminal
}

/// The symbols of `node` as they appear in its grammar rule: its children,
/// without the layout between them, and with the children of a repetition,
/// an optional, or a group in place of that node. For `Annotation = "@"
/// TypeName Values?`, the symbols are the `@`, the name, and the values.
fn symbols(node: ParseTree<'_>) -> Vec<ParseTree<'_>> {
    let mut symbols = Vec::new();
    for child in node.children() {
        if child.kind() == NodeKind::Nonterminal && child.origin().is_some() {
            symbols.extend(self::symbols(child));
        } else if Some(child.display_name()) != JavaGrammar::LAYOUT_NAME {
            symbols.push(child);
        }
    }
    symbols
}

/// The tokens below `node`, without the layout.
fn tokens(node: ParseTree<'_>) -> Vec<ParseTree<'_>> {
    if node.kind() == NodeKind::Token {
        return vec![node];
    }
    symbols(node).into_iter().flat_map(tokens).collect()
}

/// The spans of the escape sequences in `text`, the characters of the literal
/// at `span`. An escape sequence is one of:
/// - a backslash and one character, such as `\n`
/// - an octal escape of up to three digits, such as `\0`
/// - a Unicode escape, such as `\u0041`, whose `u` can repeat
fn escapes(span: Span, text: &[char]) -> Vec<Span> {
    let mut escapes = Vec::new();
    let mut index = 0;
    while index < text.len() {
        if text[index] != '\\' {
            index += 1;
            continue;
        }
        let rest = &text[index + 1..];
        let length = match rest.first() {
            Some('u') => {
                let us = rest.iter().take_while(|&&c| c == 'u').count();
                let hex = rest[us..]
                    .iter()
                    .take(4)
                    .take_while(|c| c.is_ascii_hexdigit())
                    .count();
                us + hex
            }
            // Three digits only when the first is 0 to 3, as `\377` is the
            // largest octal escape.
            Some(&first @ '0'..='7') => rest
                .iter()
                .take(if first <= '3' { 3 } else { 2 })
                .take_while(|c| ('0'..='7').contains(c))
                .count(),
            Some(_) => 1,
            None => 0,
        };
        let end = index + 1 + length;
        let start = span.left_extent;
        escapes.push(Span::new(start + index as u32, start + end as u32));
        index = end;
    }
    escapes
}

#[cfg(test)]
mod tests {
    use super::*;

    fn escaped(literal: &str) -> Vec<String> {
        let text: Vec<char> = literal.chars().collect();
        escapes(Span::new(0, text.len() as u32), &text)
            .into_iter()
            .map(|escape| {
                text[escape.left_extent as usize..escape.right_extent as usize]
                    .iter()
                    .collect()
            })
            .collect()
    }

    #[test]
    fn escapes_of_a_java_literal() {
        assert_eq!(escaped(r#""a\n\"b""#), [r"\n", r#"\""#]);
        assert_eq!(escaped(r"'\u0041' '\uu00e9x'"), [r"\u0041", r"\uu00e9"]);
        assert_eq!(escaped(r#""\0\377\8""#), [r"\0", r"\377", r"\8"]);
        // `\47` and the character 7.
        assert_eq!(escaped(r#""\477""#), [r"\47"]);
    }
}
