# DealerOnSubmission -> Sales Tax

In the following project I have created a simulation of Shopping at a Store. 
The User is able to select from a menu, creted from a text file, or input their own custom Item that they would like to purchase. 

While I know this was a simple project I wanted to make sure that it was maintainbale as well as scalable. 

I made an abstract class for Product and allowed Item to extend it. I did this so that in case we want to add 
a different type of Item with more attributes different than regular Items then it could be done simply be creating and 
extending another class. 

I created two interfaces. One for the Menun and one for the Shopping cart. Assuming that 
neither will need to change or expand as often as an Item might but still be able to implement them in a different type of cart or menu. (i.e. membership menu etc)

The regularBasket class extends the BasketType interface, the regularMenu extends the MenuType interface. The Item object 
is what I used to parse my text file as well as user input so that all Items can be referred to more easily. 

The Controller class is the heart of the code. It's execution is what allows the user to interact with the application. 
The Userview class is built for all actions that the user may need including, but not limited to, displaying the recipet, displaying menu options etc.  


# How to Run 

In order to run the code please copy the SSH link, perform a git clone on your local machine.

Once you've done so simple enter into the src folder and type in the following command "java Main Products.txt"

The program will then begin.

The user has the privillege to enter in a new item into the Products.txt file 

(Please use the proper format)
You will need the following parameters if you wish to enter in a value manullay 

choiceNumber - a number used to make selecting an item easier.

nameOfItem - the name of the item

cost - price of the item

taxOrNot - simply write Tax if taxable, nontax if not

importedOrNot - simply write imprt if imported, nonimprt if not

Ex of how to input manually into the textfile -> choiceNumber,nameOfItem,cost,taxOrNot,importedOrNot

!!!!!-> Be sure to separate items only using commas. That is my assigned delimitter.<-!!!!!

If the user does not wish to enter it into the text file then they may choose option 3 prompted by the code and enter it in more easily. 

