val whitespace = setOf(' ', '\r', '\t')
val letters = ('a'..'z') + ('A'..'Z') + '_'
val digits = ('0'..'9')

val keywords = mapOf(
    "and" to TokenType.AND,
    "class" to TokenType.CLASS,
    "else" to TokenType.ELSE,
    "false" to TokenType.FALSE,
    "for" to TokenType.FOR,
    "fun" to TokenType.FUN,
    "if" to TokenType.IF,
    "nil" to TokenType.NIL,
    "or" to TokenType.OR,
    "print" to TokenType.PRINT,
    "return" to TokenType.RETURN,
    "super" to TokenType.SUPER,
    "this" to TokenType.THIS,
    "true" to TokenType.TRUE,
    "var" to TokenType.VAR,
    "while" to TokenType.WHILE,
    )

class Scanner(val source: String) {
    private val tokens: ArrayList<Token> = ArrayList()
    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): ArrayList<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens;
    }

    private fun scanToken() {
        val c = advance()

        when (c) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';'-> addToken(TokenType.SEMICOLON)
            '*'-> addToken(TokenType.STAR)
            '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance()
                    }
                } else {
                    addToken(TokenType.SLASH)
                }
            }
            in whitespace -> {}
            '\n' -> {
                line++
            }
            '"' -> string()
            in digits -> number()
            in letters -> identifier()
            else -> {
                addToken(TokenType.ERR)
            };
        }
    }


    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    private fun peek(): Char {
        if (isAtEnd()) {
            return Char.MIN_VALUE
        }
        return source[current]
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) {
            return Char.MIN_VALUE
        }
        return source[current + 1]
    }

    private fun advance(): Char {
        current++
        return source[current - 1]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }


    private fun match(expected: Char): Boolean {
        if (isAtEnd()) {
            return  false
        }
        if (source[current] != expected) {
            return  false
        }
        current++
        return true
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++
            }
            advance()
        }

        if (isAtEnd()) {
            return
        }

        advance()


        // Trim the surrounding quotes.
        val value = source.substring(start + 1, current - 1)
        addToken(TokenType.STRING, value)
    }

    private fun number() {
        while (peek() in digits) {
            advance()
        };

        // Look for a fractional part.
        if (peek() == '.' && peekNext() in digits) {
            // Consume the "."
            advance();

            while (peek()in digits) {
                advance()
            };
        }

        addToken(
            TokenType.NUMBER,
            source.substring(start, current).toDoubleOrNull());
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return c in letters || c in digits
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) {
            advance()
        };

        val text = source.substring(start, current)
        var type: TokenType? = keywords.get(text)
        if (type == null) {
            type = TokenType.IDENTIFIER
        }
        addToken(type)
    }
}