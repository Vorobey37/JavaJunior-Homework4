package org.example.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "courses")
public class Course {

    private static final String[] titles = new String[] { "История", "Математика", "Базы данных", "Java", "Java Junior", "Литература", "Музыка", "Машиностроение", "Frontend", "Backend" };
    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;

    public Course(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Course() {
    }

    public static Course create(){
        return new Course(titles[random.nextInt(titles.length)], random.nextInt(2, 6));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void updateTitle(){
        title = titles[random.nextInt(titles.length)];
    }

    public void updateDuration(){
        duration = random.nextInt(2, 6);
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
