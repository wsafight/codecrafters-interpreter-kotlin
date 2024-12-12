class Tokenizer(input: String) {
    private var remaining = input
    private var line = 1

    fun nextToken(): Token {
        if (remaining.isEmpty()) {
            return Token(TokenType.EOF, "", null)
        }
        return when (remaining[0]) {
            '(' -> {
                remaining = remaining.substring(1)
                Token(TokenType.LEFT_PAREN, "(", null)
            }
            ')' -> {
                remaining = remaining.substring(1)
                Token(TokenType.RIGHT_PAREN, ")", null)
            }
            '{' -> {
                remaining = remaining.substring(1)
                Token(TokenType.LEFT_BRACE, "{", null)
            }
            '}' -> {
                remaining = remaining.substring(1)
                Token(TokenType.RIGHT_BRACE, "}", null)
            }
            '*' -> {
                remaining = remaining.substring(1)
                Token(TokenType.STAR, "*", null)
            }
            '.' -> {
                remaining = remaining.substring(1)
                Token(TokenType.DOT, ".", null)
            }
            ',' -> {
                remaining = remaining.substring(1)
                Token(TokenType.COMMA, ",", null)
            }
            '+' -> {
                remaining = remaining.substring(1)
                Token(TokenType.PLUS, "+", null)
            }
            '-' -> {
                remaining = remaining.substring(1)
                Token(TokenType.MINUS, "-", null)
            }
            ':' -> {
                remaining = remaining.substring(1)
                Token(TokenType.COLON, ":", null)
            }
            ';' -> {
                remaining = remaining.substring(1)
                Token(TokenType.SEMICOLON, ";", null)
            }
            '=' -> {
                if (remaining.length >= 2 && remaining[1] == '=') {
                    remaining = remaining.substring(2)
                    Token(TokenType.EQUAL_EQUAL, "==", null)
                } else {
                    remaining = remaining.substring(1)
                    Token(TokenType.EQUAL, "=", null)
                }
            }
            '!' -> {
                if (remaining.length >= 2 && remaining[1] == '=') {
                    remaining = remaining.substring(2)
                    Token(TokenType.BANG_EQUAL, "!=", null)
                } else {
                    remaining = remaining.substring(1)
                    Token(TokenType.BANG, "!", null)
                }
            }
            '<' -> {
                if (remaining.length >= 2 && remaining[1] == '=') {
                    remaining = remaining.substring(2)
                    Token(TokenType.LESS_EQUAL, "<=", null)
                } else {
                    remaining = remaining.substring(1)
                    Token(TokenType.LESS, "<", null)
                }
            }
            '>' -> {
                if (remaining.length >= 2 && remaining[1] == '=') {
                    remaining = remaining.substring(2)
                    Token(TokenType.GREATER_EQUAL, ">=", null)
                } else {
                    remaining = remaining.substring(1)
                    Token(TokenType.GREATER, ">", null)
                }
            }
            '/' -> {
                if (remaining.length >= 2 && remaining[1] == '/') {
                    var i = 2
                    while (remaining[i] != '\n') {
                        i++
                    }
                    remaining = remaining.substring(i)
                } else {
                    remaining = remaining.substring(1)
                    Token(Token.SLASH, "/", null)
                }
            }
            else -> {
                val char = remaining[0]
                remaining = remaining.substring(1)
                Token(TokenType.ERR, "", null).apply {
                    errLine = line
                    errChar = char
                }
            }
        }
    }

}