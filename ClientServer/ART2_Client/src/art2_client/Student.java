/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package art2_client;

/**
 *
 * @author sander
 */
import java.io.Serializable;
// Class: Student
class student implements Serializable {
   int id;
   String name;
   String course;
   student(int id,String name,String course) {
     this.id=id;
     this.name=name;
     this.course=course;
    }
   void showDetails() {
     System.out.println("Id:"+id);
     System.out.println("Name:"+name);
     System.out.println("Course:"+course);
    }  
 }
