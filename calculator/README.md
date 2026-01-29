# Text-Based Calculator

A clean, object-oriented Java calculator that evaluates assignment expressions with support for arithmetic operations, increment/decrement operators, and variable management.

## Features

✅ **Arithmetic Operations**: `+`, `-`, `*`, `/`  
✅ **Assignment Operators**: `=`, `+=`, `-=`, `*=`, `/=`  
✅ **Increment/Decrement**: `++i` (prefix), `i++` (postfix), `--i`, `i--`  
✅ **Operator Precedence**: Correct evaluation order (parentheses, unary, multiplicative, additive, assignment)  
✅ **Variable Storage**: Store and retrieve variable values across expressions  
✅ **Clean Architecture**: Separated concerns with dedicated packages for lexing, parsing, evaluation, and storage  

## Example Usage

### Input
```
i = 0
j = ++i
x = i++ + 5
y = (5 + 3) * 10
i += y
```

### Output
```
(i=82,j=1,x=6,y=80)
```

**Explanation**:
1. `i = 0` → i is 0
2. `j = ++i` → increment i to 1, then assign to j → i=1, j=1
3. `x = i++ + 5` → i is 1, add 5 to get 6, assign to x, then increment i → i=2, x=6
4. `y = (5 + 3) * 10` → (8) * 10 = 80 → y=80
5. `i += y` → i = 2 + 80 = 82 → i=82

Final state: `(i=82,j=1,x=6,y=80)`

## Architecture

### Package Structure

```
src/main/java/com/example/
├── App.java                    # Application entry point
├── lexer/                      # Tokenization layer
│   ├── Lexer.java             # Regex-based tokenizer
│   ├── Token.java             # Token representation
│   └── TokenType.java         # Token type enumeration
├── parser/                     # Expression parsing layer
│   ├── Parser.java            # Recursive descent parser
│   ├── ASTNode.java           # Base AST node class
│   ├── LiteralNode.java       # Numeric literal
│   ├── VariableNode.java      # Variable reference
│   ├── BinaryOpNode.java      # Binary operations (+, -, *, /)
│   ├── UnaryOpNode.java       # Unary operations (++, --)
│   └── AssignmentNode.java    # Assignment operations (=, +=, etc.)
├── evaluator/                  # Evaluation orchestrator
│   └── Evaluator.java         # Coordinates lexing, parsing, evaluation
└── store/                      # Variable storage
    └── VariableStore.java     # Variable state management
```

### Data Flow

```
Input Expression
       ↓
   Lexer (Tokenization)
       ↓
   Parser (AST Building)
       ↓
   Evaluator (Execution)
       ↓
   VariableStore (State Management)
       ↓
   Output (Variable Values)
```

## How It Works

### 1. Lexer - Tokenization
Converts input string into tokens using Regex patterns:
```
Input: "x = 5 + 3"
Output: [IDENTIFIER(x), ASSIGN(=), NUMBER(5), PLUS(+), NUMBER(3), EOF]
```

### 2. Parser - AST Building
Builds an Abstract Syntax Tree respecting operator precedence via recursive descent:
```
Precedence (lowest to highest):
1. Assignment (=, +=, -=, *=, /=)
2. Additive (+, -)
3. Multiplicative (*, /)
4. Unary (++i, --i, prefix)
5. Postfix (i++, i--, postfix)
6. Primary (literals, variables, parentheses)
```

**Example AST for "2 + 3 * 4"**:
```
        BinaryOp(+)
        /         \
    Literal(2)   BinaryOp(*)
                 /         \
            Literal(3)   Literal(4)
```

### 3. Evaluator - Execution
Traverses the AST and evaluates it recursively:
- Calls appropriate methods based on node type
- Manages state through VariableStore
- Handles side effects (++/--)

### 4. VariableStore - State
HashMap-based storage with sorted output format:
```java
store.set("x", 10);
store.set("y", 20);
store.toString() → "(x=10,y=20)"
```

## Building & Running

### Prerequisites
- Java 7+
- Maven 3.0+

### Build
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

### Test
```bash
mvn test
```

## Usage

Run the application and enter expressions one per line:

```bash
$ mvn exec:java -Dexec.mainClass="com.example.App"
Text-based Calculator. Enter expressions (Ctrl+D or Ctrl+Z to finish):

i = 0
j = ++i
x = i++ + 5
y = (5 + 3) * 10
i += y

Result: (i=82,j=1,x=6,y=80)
```

## Test Coverage

**28 comprehensive unit tests** covering:

- **Lexer Tests** (4): Number tokenization, identifiers, operators, parentheses
- **Parser Tests** (4): Literals, variables, binary ops, assignments
- **Evaluator Tests** (14): Complete expression sequences, complex nested expressions, edge cases
- **VariableStore Tests** (1): State management and output formatting

Run tests with:
```bash
mvn test
```

All tests pass: ✅ 28/28

## Key Design Patterns

### Recursive Descent Parser
Implemented as a hierarchy of parsing methods, each handling a precedence level:
- `parseAssignment()` - lowest precedence
- `parseAdditive()`
- `parseMultiplicative()`
- `parseUnary()`
- `parsePostfix()`
- `parsePrimary()` - highest precedence

### Visitor Pattern (AST)
Each AST node type inherits from `ASTNode` with an `evaluate()` method for polymorphic evaluation.

### Builder Pattern (Evaluator)
Orchestrates the parsing pipeline cleanly in sequence.

## Error Handling

The application provides meaningful error messages for:
- **Lexer errors**: Unrecognized tokens
- **Parser errors**: Invalid syntax, unexpected tokens, missing parentheses
- **Evaluator errors**: Division by zero, undefined variables, invalid operations

Example:
```
Error: Division by zero
Error: Undefined variable: z
Error: Assignment target must be a variable
```

## Code Quality

- **Clean Code**: Clear naming, single responsibility, minimal complexity
- **Separation of Concerns**: Distinct packages for each responsibility
- **Testability**: All classes independently testable via unit tests
- **Documentation**: Comprehensive inline comments and this README

## Author

Created as a coding exercise demonstrating OOP principles, recursive descent parsing, and AST evaluation.

## License

MIT License - Feel free to use and modify for educational purposes.
