# directories
SRC_DIR = ./src/gamesleague
BIN_DIR = ./bin
TEST_DIR = ./TestSystem

# compile all Java files
build:
	javac -d $(BIN_DIR) $(SRC_DIR)/*.java

# compile the test files
test-build: build
	javac -cp $(BIN_DIR) -d $(BIN_DIR) $(TEST_DIR)/TestPlayerApp.java

# run the test program
run: test-build
	java -cp $(BIN_DIR) TestSystem.TestPlayerApp

