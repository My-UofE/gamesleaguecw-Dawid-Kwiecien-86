# directories
SRC_DIR = ./src/gamesleague
BIN_DIR = ./bin
TEST_DIR = ./TestSystem

# compile all Java files
build:
	javac -d $(BIN_DIR) $(SRC_DIR)/*.java

# compile the test files
test-build: build
	javac -cp $(BIN_DIR) -d $(BIN_DIR) $(TEST_DIR)/TestPlayerApp.java $(TEST_DIR)/TestLeagueApp.java

# run individual test programs
run-p: test-build
	java -cp $(BIN_DIR) TestSystem.TestPlayerApp

run-l: test-build
	java -cp $(BIN_DIR) TestSystem.TestLeagueApp

# truncate the .ser files to reset stored data
truncate:
	truncate -s 0 ./src/gamesleague/save/Players.ser
	truncate -s 0 ./src/gamesleague/save/Leagues.ser
	truncate -s 0 ./src/gamesleague/save/nextId.ser
	truncate -s 0 ./src/gamesleague/save/nextLeagueId.ser