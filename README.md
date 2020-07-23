# Sales Tax

In the following project I have created a simulation of Shopping at a Store. 
The User is able to select from a menu, created from a text file, or input their own custom Item that they would like to purchase. 

While I know this is a simple project I wanted to make sure that it was maintainable as well as scalable. 

I made an abstract class for Product and allowed Item to extend it. I did this so that in case we want to add 
a different type of Item with more attributes different than regular Items then it could be done simply be creating and 
extending another class. 

I created two interfaces. MenuType and BasketType. Assuming that neither will need to change or expand as often as an Item might 
but still be able to implement them in a different type of cart or menu. (i.e. membership menu, membership cart etc)

The regularBasket class extends the BasketType interface, the regularMenu extends the MenuType interface. The Item object 
is what I used to parse my text file as well as user input so that all Items can be referred to more easily. 

The Controller class is the heart of the code. It's execution is what allows the user to interact with the application. 
The Userview class is built for all actions that the user may need including, but not limited to, displaying the receipt, displaying menu options etc.  

One of the ways I got around identifying items as taxable or imported is by having the user input the information since there was no concrete way of making sure all items entered were in the correct category. 

Any adjustments or additions can easily be made to the code to add any more features or functionality. It is structured in a modularized format to allow for easy reuse of code in different implementation. 

# How to Run 

This is how you can set up a clean-room environment using Docker from your terminal:

`brew cask install docker`

It is not imperative that you install Docker. 

The only requirement is that you use Java 12.

```
# From your teminal
docker run --rm -it openjdk:12 bash

#From within the container
curl -LO https://github.com/LightDvlpr/ShoppingCartSimulation/archive/master.tar.gz
tar xf master.tar.gz

cd ShoppingCartSimulation-master/src/

# these two lines will run the program
javac Main.java
java Main ../Products.txt
```

The program will then begin.

# Additional info

The user has the privilege to enter in a new item into the Products.txt file 

(Please use the proper format)

You will need the following parameters if you wish to enter in a value manually 

`choiceNumber - a number used to make selecting an item easier.`

`nameOfItem - the name of the item`

`cost - price of the item`

`taxOrNot - simply write Tax if taxable, nontax if not`

`importedOrNot - simply write imprt if imported, nonimprt if not`

Ex of how to input manually into the textfile -> ```choiceNumber,nameOfItem,cost,taxOrNot,importedOrNot```

Be sure to separate items only using commas. That is my assigned `delimitter`.

If the user does not wish to enter it into the text file then they may choose `option 3` prompted by the code and enter it in more easily. 


# Method Test cases

I set up test cases for the methods in RegularBasket and RegularMenu. 

I added all the items with their names, quantities, etc in lines 16 to 24. 

If you run the tests you'll see that my tests return the exact answers as shown in the pdf. 

You may of course adjust this accordingly or even add your own items. 
