# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "SplashScreen"

# HACK: myShape.java and vecmath are compiled regardless if needed
all:
	@echo "Compiling..."
	javac -cp vecmath-1.5.1.jar $(NAME).java SnakeGame.java Snake.java Node.java Fruit.java Direction.java

run: all
	@echo "Running..."
	java -cp "vecmath-1.5.1.jar:." $(NAME)