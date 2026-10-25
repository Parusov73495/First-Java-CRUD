
package com.example.first_java_crud;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class StudentController {

    private final List<Student> students = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(0);

    public StudentController() {
        students.add(new Student(counter.incrementAndGet(), "Alex", "alex@example.com"));
        students.add(new Student(counter.incrementAndGet(), "Maria", "maria@example.com"));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Student student) {
        student.setId(counter.incrementAndGet());
        students.add(student);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student updated) {
        students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .ifPresent(s -> {
                    s.setName(updated.getName());
                    s.setEmail(updated.getEmail());
                });
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        students.removeIf(s -> s.getId().equals(id));
        return "redirect:/";
    }

    public static class Student {
        private Long id;
        private String name;
        private String email;

        public Student() {}

        public Student(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
