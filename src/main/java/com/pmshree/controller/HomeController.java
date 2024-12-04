package com.pmshree.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Set current page for navigation
        model.addAttribute("currentPage", "home");
        model.addAttribute("pageTitle", "PM Shree - Home");

        // School Information
        SchoolInfo schoolInfo = new SchoolInfo(
                "PM Shree Bahlolpur",
                "PM SHRI School is a centrally sponsored scheme by the Government of India. This initiative is intended to develop more than 14500 PM SHRI Schools managed by Central Government/State/UT Government/local bodies including KVS and NVS in which every student feels welcomed and cared for, where a safe and stimulating learning environment exists, where a wide range of learning experiences are offered, and where good physical infrastructure and appropriate resources conducive to learning are available to all students.\n" +
                        "It will nurture students in a way that they become engaged, productive, and contributing citizens for building an equitable, inclusive, and plural society as envisaged by the National Education Policy 2020."
        );
        model.addAttribute("schoolInfo", schoolInfo);

        // Notice Board
        NoticeBoard noticeBoard = new NoticeBoard("/assets/img/nature/image5.jpg");
        model.addAttribute("noticeBoard", noticeBoard);

        // Quick Updates
        QuickUpdates quickUpdates = new QuickUpdates("/assets/img/nature/image5.jpg");
        model.addAttribute("quickUpdates", quickUpdates);

        // Skills Section
        List<Skill> skills = Arrays.asList(
                new Skill("Nipun Bharat", "Description here...", "icon ion-ios-star-outline"),
                new Skill("Learning By Doing", "Description here...", "icon ion-ios-lightbulb-outline"),
                new Skill("NEP 2020", "Description here...", "icon ion-ios-gear-outline")
        );
        model.addAttribute("skills", skills);

        // About Section
        AboutSection aboutSection = new AboutSection(
                "About School",
                "School description here...",
                "/assets/img/tech/image6.png"
        );
        model.addAttribute("aboutSection", aboutSection);

        // Social Links
        SocialLinks socialLinks = new SocialLinks(
                "#",
                "#",
                "#",
                "#"
        );
        model.addAttribute("socialLinks", socialLinks);

        return "index";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "home";
    }

}

// Model classes
@Data
@AllArgsConstructor
class SchoolInfo {
    private String name;
    private String description;
}

@Data
@AllArgsConstructor
class NoticeBoard {
    private String imageUrl;
}

@Data
@AllArgsConstructor
class QuickUpdates {
    private String imageUrl;
}

@Data
@AllArgsConstructor
class Skill {
    private String title;
    private String description;
    private String iconClass;
}

@Data
@AllArgsConstructor
class AboutSection {
    private String title;
    private String description;
    private String imageUrl;
}

@Data
@AllArgsConstructor
class SocialLinks {
    private String followUsUrl;
    private String facebookUrl;
    private String instagramUrl;
    private String twitterUrl;
}
