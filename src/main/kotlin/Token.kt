enum class TokenType {
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    STAR, DOT, COMMA, 
    SEMICOLON,COLON,
    PLUS, MINUS,   
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    LESS, LESS_EQUAL,
    GREATER, GREATER_EQUAL,
    SLASH,
    COMMENT,
    EOF, ERR, 
}

class Token(val type: TokenType, val lexeme: String, val literal: String?) {
    var errLine = 0
    var errChar = ' '
    override fun toString(): String {
        return "${type.name} $lexeme $literal"
    }
}