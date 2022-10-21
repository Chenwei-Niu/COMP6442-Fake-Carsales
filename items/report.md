# [RickAstley] Report

The following is a report template to help your team successfully provide all the details necessary for your report in a structured and organised manner. Please give a straightforward and concise report that best demonstrates your project. Note that a good report will give a better impression of your project to the reviewers.

*Here are some tips to write a good report:*

* *Try to summarise and list the `bullet points` of your project as many as possible rather than give long, tedious paragraphs that mix up everything together.*

* *Try to create `diagrams` instead of text descriptions, which are more straightforward and explanatory.*

* *Try to make your report `well structured`, which is easier for the reviewers to capture the necessary information.*

*We give instructions enclosed in square brackets [...] and examples for each sections to demonstrate what are expected for your project report.*

*Please remove the instructions or examples in `italic` in your final report.*

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Conflict Resolution Protocol](#conflict-resolution-protocol)
4. [Application Description](#application-description)
5. [Application UML](#application-uml)
6. [Application Design and Decisions](#application-design-and-decisions)
7. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
8. [Testing Summary](#testing-summary)
9. [Implemented Features](#implemented-features)
10. [Team Meetings](#team-meetings)

## Team Members and Roles

| UID | Name | Role |
| :--- | :----: | ---: |
| u7377070 | Chenwei Niu | Leader;Trouble_shooter |
| u7389455 | Zice Yan | Designer |
| u6441152 | Xinyu Wu | Doer |
| u6557983 | Canxuan Gang | Supporter |

## Summary of Individual Contributions

*[Summarise the contributions made by each member to the project, e.g. code implementation, code design, UI design, report writing, etc.]*

*[Code Implementation. Which features did you implement? Which classes or methods was each member involved in? Provide an approximate proportion in pecentage of the contribution of each member to the whole code implementation, e.g. 30%.]*

*Here is an example:*

*UID1, Name1, I contribute 30% of the code. Here are my contributions:*
* A.class
* B.class: function1(), function2(), ...
* ....

*you should ALSO provide links to the specified classes and/or functions*

*[Code Design. What design patterns, data structures, did the involved member propose?]*

*[UI Design. Specify what design did the involved member propose? What tools were used for the design?]*

*[Report Writing. Which part of the report did the involved member write?]*

*[Slide preparation. Were you responsible for the slides?]*

*[Miscellaneous contributions. You are welcome to provide anything that you consider as a contribution to the project or team.]*



- #### **u7377070, Chenwei Niu**

  **Code Implementation**: I contribute 35% of the code implementation. Here are my contributions:

  - entire package [com.example.buy.parser](../Buy/app/src/main/java/com/example/buy/parse)
  - entire package [com.example.buy.view](../Buy/app/src/main/java/com/example/buy/view)
  - [BuyFragment.class](../Buy/app/src/main/java/com/example/buy/fragment/BugFragment.java), [SearchFragment.class](../Buy/app/src/main/java/com/example/buy/fragment/SearchFragment.java),[Car.class](../Buy/app/src/main/java/com/example/buy/entity/Car.java), [Market.class](../Buy/app/src/main/java/com/example/buy/entity/Market.java)
  - [UploadCarActivity.class](../Buy/app/src/main/java/com/example/buy/activity/UploadCarActivity.java),[KeyBoardUtils.class](../Buy/app/src/main/java/com/example/buy/utils/KeyBoardUtils.java)
  - [AVLTree.class](../Buy/app/src/main/java/com/example/buy/avltree/AvlTree.java)`:preOrderTraverse(), findLessOrEqualThan(),findLessThan(),findGreaterOrEqualThan(),find()`

  

  **Code Design**: I proposed Iterator pattern,  Singleton pattern, Observer pattern, factory pattern and DAO pattern.

  Finally, we decided to use the three pattern **Iterator pattern**,  **Singleton pattern **and **DAO pattern**.

  

  **UI design**:  I proposed use minimalist style as out UI design. Mainly use white, grey, black and blue to build the app.

  

  **Report Writing:** I am obligated to write the details of search feature, and a part of application design and decision

  

  **Miscellaneous contributions**: I wrote a spider to get real car data from www.carsales.com

  

- #### **Zice Yan**

  - 

- #### **u6557983, Canxuan Gang**
    **Code Implementation**: I contribute 20% of the code implementation. Here are my contributions:

  - [Car.class](../Buy/app/src/main/java/com/example/buy/entity/Car.java)---(Announcement: Participated in only part of the modification, main work is still implemented by Chenwei Niu)

  - [User.class](../Buy/app/src/main/java/com/example/buy/entity/User.java)---(Announcement: Participated in only part of the modification, main work is still implemented by Chenwei Niu)

  - [FollowFragment.class](../Buy/app/src/main/java/com/example/buy/fragment/FollowFragment.java)---(main work for project)

  - [CarViewAdapter.class](../Buy/app/src/main/java/com/example/buy/view/CarViewAdapter.java)---(Announcement: Participated in only part of the modification, main work is still implemented by Chenwei Niu)

  **UI design**: For the UI design part, I participated in a very small part, I made a "follow" button, that user can click this button to view the list of followed vehicles during the search.

  **Report Writing**: For this project, I craete a "follow" function in our APP, where users are able to follow a car and notified some events if there are any changes such as removed and add new cars. I construct this functionality based on 
Observer Pattern, where users are the subscribers and cars are the subjects. For simplicity and convenience, I do not add extra interfaces to implement but directly to add functions in relevant classes. Besides, relevant classes have been modified and created to support this functionality.

  More Specifically:

  [Car.class](../Buy/app/src/main/java/com/example/buy/entity/Car.java)
  For Car.java, I edit the variable favouriteUserList to record followed users.

  [User.class](../Buy/app/src/main/java/com/example/buy/entity/User.java)
  For User.java, I added favouritaCars to mark the car current user followed and to subscribe it, which 
was later modified by my teammates, combining mentioned functions in Fragment.java.

  [FollowFragment.class](../Buy/app/src/main/java/com/example/buy/fragment/FollowFragment.java)
  For FollowFragment.java, I construct this corresponding to the page of follow items and complete its 
functionality.

  [CarViewAdapter.class](../Buy/app/src/main/java/com/example/buy/view/CarViewAdapter.java)
  for CarViewAdapter.java, I modified it to better complete the car, which I first 
created a java class but later was modifed by my teammates to combine my methods 
in CarViewAdapter.java.following function based on Observe Pattern.


- #### **Xinyu Wu**

  - 



## Conflict Resolution Protocol

*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

- If a team member's weekly meeting assignment is not completed, we will reduce his contribution to the project and reassign the task.
- If any team member is unable to attend the weekly meeting due to special reasons, we will record the meeting screen and send it to him.
- If a team member is ill and unable to carry out the project, we will distribute his current task equally among the remaining members.
- If there is a conceptual conflict in the project design, we will vote within the group and implement the plan with the highest number of votes.
- In order to avoid any conflicts in the team, we will use group chat to communicate the task progress and difficulties in the project.
- When a group member has a technical problem, he can send the problem to our group chat platform, and everyone will give their own suggestions.
- When problems arise in the group, the team members should be active in solving them and can offer constructive criticism. The purpose of this is to offer frank and direct opinions to solve the problem, but also to support and point out the good work done by the team members.

[Please check the details in the last paragraph of meeting 1 record](meeting1.md)


## Application Description

"REAL CARSALES" is a used car trade application developed for people with used car purchase intent. It provides concrete information on existing used cars in stock across Australia for 7 car brands.  Users can browse used car information on the app, and search for vehicles based on brand, price, year, odometer and other conditions according to their own needs. Users can also bookmark their favourite cars. In addition to this, the app also provides an in-software chat function, enabling buyers and sellers to communicate, and car lovers to communicate with each other.



**Application Use Cases and or Examples**

*[Provide use cases and examples of people using your application. Who are the target users of your application? How do the users use your application?]*

*Here is a pet training application example*

*Molly wants to inquiry about her cat, McPurr's recent troublesome behaviour*
1. *Molly notices that McPurr has been hostile since...*
2. *She makes a post about... with the tag...*
3. *Lachlan, a vet, writes a reply to Molly's post...*
4. ...
5. *Molly gives Lachlan's reply a 'tick' response*

*Here is a map navigation application example*

*Targets Users: Drivers*

* *Users can use it to navigate in order to reach the destinations.*
* *Users can learn the traffic conditions*
* ...

*Target Users: Those who want to find some good restaurants*

* *Users can find nearby restaurants and the application can give recommendations*
* ...

*List all the use cases in text descriptions or create use case diagrams. Please refer to https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-use-case-diagram/ for use case diagram.*

## Application UML

![uml_1](./images/uml_1.PNG) <br>
![uml_2](./images/uml_2.PNG) <br>
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

## Application Design and Decisions

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design. Here is an example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *LinkedList*

   * *Objective: It is used for storing xxxx for xxx feature.*

   * *Locations: line xxx in XXX.java, ..., etc.*

   * *Reasons:*

     * *It is more efficient than Arraylist for insertion with a time complexity O(1)*

     * *We don't need to access the item by index for this feature*

2. ...

3. ...

**Data Structures**

*[What data structures did your team utilise? Where and why?]*

**Design Patterns**

*[What design patterns did your team utilise? Where and why?]*

**Grammar(s)**

Production Rules:
    
    <Non-Terminal> ::= <some output>
    <Non-Terminal> ::= <some output>

*[How do you design the grammar? What are the advantages of your designs?]*

*If there are several grammars, list them all under this section and what they relate to.*

**Tokenizer and Parsers**

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

**Surprise Item**

*[If you implement the surprise item, explain how your solution addresses the surprise task. What decisions do your team make in addressing the problem?]*

**Other**

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

## Summary of Known Errors and Bugs

1. *Bug 1:*

- When clicking the exactly third car's Favourite-Vehicle button/Trash button in main/search/follow/mine page,

   there will be a jump towards the first car. 

  

  This is because each page can only display two and a half blocks of cars information, but user can still click the favourite button of the third car. Therefore, when the page is not scrolled down to see all the information of the third car, the system thinks the user is still in the first page, then when you click on the favourite of the third car, it will jump to the first top of the list.

  

  When all the information of the third vehicle is displayed, the system thinks that the user has scrolled down, and then there will be no bug if you click the favourite again.

  <img src="./images/bug1.png" alt="bug1.png" style="zoom: 25%;" /><img src="./images/bug2.png" alt="image-20221021131952867" style="zoom:25%;" />

  

## Testing Summary

*[What features have you tested? What is your testing coverage?]*

*Here is an example:*

- *Number of test cases: ...*

- *Code coverage: ...*

- *Types of tests created: ...*

*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

## Implemented Features

*[What features have you implemented?]*

*Here are some examples:*

Feature Category: Privacy <br>
*Implemented features:*
1. Feature 1: **Users may ... . (easy)**
   * Class X, methods Z, Y, Lines of code: 10-100
   * Class Y, methods K, L, M, Lines of code: 35-150
   * Your description: ...
<br>
2. Feature 2: **A user can only ... . (medium)**
<br>

Feature Category: Firebase Integration <br>
*Implemented features:* <br>
1. **Use Firebase to implement user Authentication/Authorisation. (easy)**
   * Class A: methods A, B, C, lines of code: whole file
   * …

*List all features you have completed in their separate categories with their difficulty classification. If they are features that are suggested and approved, please state this somewhere as well.*

## Team Meetings

*Here is an example (you could start numbering your meetings from 1):*

- *[Team Meeting 0](./meeting0.md)*
- ...

* Link to the minutes of your meetings as above. There must be at least 4 team meetings. 
Note that you must commit your minute meetings shortly after your meeting has taken place (e.g., within 24h), otherwise your meeting minute will not be accepted.
Uour meetings should also have a reasonable date span across Week 6 to 11.*

