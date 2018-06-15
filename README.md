# ComNet
(this project was created during my time at unversity)

Internal communication network designed for educational institutions.

Capabilities:
- User administration with privileges (students, "semi-admins", admins)
  - "semi-admins" can:
    - create new students
    - creat new student groups
    - see other users
  - admins can:
    - create new students
    - create new student groups
    - create new "semi-admins"
    - create new admins
    - change role of all users
  - students can:
    - not access the administration area and thus not do anything there
- Chatting:
  - students can chat with other students
  - group conversations between multiple students or whole student groups possible
- Creating groups:
  - students can create groups
  - the creator of a group ("group admin") can add other students and appoint additional admins
  - group admins can delete topics
  - all members can create topics and add posts there in ordert to discuss this topic

Main folder for code:
- /Code/src/main/

Main folder for frontend (JavaScript/AngularJS, HTML, CSS):
- /Code/src/main/view

Main folder for backend (Java):
- Code/src/main/java
