# Post-course Java Library System Project

This project is a simple Library Management System built using Java. With user-interaction occurring entirely via the command-line, this system allows you to login to an existing account or create a new account. Once logged in, you can view your current loans, if any, or loan a new book from those avaiable. In addition, there is a password-protected Admin account from which a Library Report can be accessed, which lists every book in the library, how many times each has been loaned and whether the book is currently on loan.

The main challenege of this project was creating persistence - i.e., ensuring that changes made during one session a carried over to the next by storing information about the users and books. This was achieved by writing user/book data to JSON files whenever it was changed, like when a new account was created or a book was loaned, and then re-generating user/book ArrayLists from these files. 

As with any interface that requires user input, a significant amount of input validation was required to ensure the system works appropriately. For example, users should not be able to loan books that are not available, nor should they be able to return books that they don't have on loan. In addition, the system is robust enough to deal with unexpected inputs and return the user to the previous page if necessary. 
