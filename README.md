# DealerOnSubmission

In the following project I have created a simulation of Shopping at a Store. 
The User is able to select from a menu, taken from a text file, or input their own custom Item that they would like to purchase. 

While I know this was a simple project I wanted to make sure that it was maintainbale as well as expandable. 

I made an abstract class for Product and allowed Item to extend it. I did this so that in case we want to add 
a different type of Item with more attributes different than Item then it could be done simply be creating and 
extending another class. 

I created two interface. One for the Menu and one for the Shopping cart. Assuming that 
neither will need to change or expand as often as an Item might. 

The basket class extends the shopping cart interface, the MenuImplmentation extends the Menu interface. The Item object 
is what I used to parse my text file as well as user input so that all Items can be referred to more easily. 

The Controller class is the heart of the code. It's execution is what allows the user to interact with the application. 
The Userview class is built for all actions that the user may need including, but not limited to, displaying the recipet, calculations etc.  


#How to Run 
