# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "Main"

# HACK: myShape.java and vecmath are compiled regardless if needed
all:
	@echo "Compiling..."
	javac -cp vecmath-1.5.1.jar $(NAME).java Controller.java Model.java Tool.java View.java View2.java

run: all
	@echo "Running..."
	java -cp "vecmath-1.5.1.jar:." $(NAME)