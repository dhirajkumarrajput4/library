package com.library.control;

import com.library.dao.BookDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.SellBook;
import com.library.model.User;

import com.library.service.BookService;
import com.library.service.SellBookService;
import com.library.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.util.List;

@Controller
public class MyController {
    private static final String User = null;

    com.library.model.User sessionUser = new User();

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    UserDao userDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    SellBookService sellBookService;

    @RequestMapping("/login")
    public String login() {
        /*
         * User user=null; model.addAttribute("user", user);
         */
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @ModelAttribute
    public com.library.model.User createUser() {
        return new User();
    }

    @RequestMapping(value = "/registerform", method = RequestMethod.POST)
    public String showRegister(@Valid @ModelAttribute("user") com.library.model.User user, BindingResult result,
                               HttpServletRequest request) throws IOException {
        if (result.hasErrors()) {
            return "register";
        } else {
            System.out.println(user.getFirstName());
            this.userService.save(user);

            return "redirect:/";
        }
    }

    @RequestMapping(value = "/loginform", method = RequestMethod.POST)
    public String checkLogin(@RequestParam("email") String Email, @RequestParam("password") String Password,
                             Model model, HttpServletRequest request, HttpSession session) {
        System.out.println(Email);
        System.out.println(Password);
        com.library.model.User checkUser = this.userDao.checkUser(Email, Password);
        if (checkUser == null) {
            session.setAttribute("msg", "Invailid Username or Password");
            session.setAttribute("clss", "alert-danger");
            return "redirect:/login";

        } else {
            if (checkUser.getUserType().trim().equals("admin")) {
                // retrieve book on home page
                List<Book> books = bookService.getBooks();
                this.sessionUser = checkUser;

                session.setAttribute("logedUser", checkUser);
                session.setAttribute("name", checkUser.getFirstName());
                session.setAttribute("usertype", checkUser.getUserType());
                session.setAttribute("id", checkUser.getId());
                session.removeAttribute("msg");
                session.removeAttribute("clss");
                return "redirect:/";
            } else {

                this.sessionUser = checkUser;
                // retrieve book on home page
                List<Book> books = bookService.getBooks();
                session.setAttribute("logedUser", checkUser);
                session.setAttribute("name", checkUser.getFirstName());
                session.setAttribute("id", checkUser.getId());
                session.removeAttribute("msg");
                session.removeAttribute("clss");
                return "redirect:/";
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session, HttpServletRequest request) {
        session.removeAttribute("logedUser");
        session.removeAttribute("name");
        session.removeAttribute("usertype");
        System.out.println(request.getServletPath());
        return "redirect:/";
    }

    /* book model attribute */
    @ModelAttribute
    public Book createBook() {
        return new Book();
    }

    @RequestMapping(value = "/registerbookform", method = RequestMethod.POST)
    public String showBooks(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpServletRequest request,
                            Model m) throws IOException {
        boolean error = false;
        if (result.hasErrors()) {
            error = true;
            m.addAttribute("error", error);
//			retrieve all books on home page
            List<Book> books = bookService.getBooks();
            m.addAttribute("books", books);
            return "index";

        } else {
            System.out.println(book.getBooktitle());
            this.bookService.save(book);
            return "redirect:/";
        }
    }

    @RequestMapping("/")
    public String getAllBook(Model model, HttpSession session) {
        com.library.model.User user = null;
        model.addAttribute("user", user);
        List<Book> books = bookService.getBooks();
//		model.addAttribute("book1",new Book());
        model.addAttribute("books", books);

        session.removeAttribute("msg");
        session.removeAttribute("clss");

        return "index";
    }

    @RequestMapping("/deletebook/{id}")
    public ModelAndView deleteBook(@PathVariable("id") int id) {
        this.bookService.deleteBook(id);
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }

    @RequestMapping("/updatebook/{id}")
    public String updateBook(@PathVariable("id") int id, Model model) {
        // get all books on home page and /bookdelete/url
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        Book bookById = this.bookDao.getById(id);
        model.addAttribute("book", bookById);
        return "index";
    }


    //	buy the book
    @RequestMapping(value = "/buy/{id}")
    public String buyTheBook(@PathVariable(name = "id") Integer id, Model model) {
        Book bookbyid = this.bookDao.getById(id);

        SellBook slb = new SellBook();

        slb.setBookName(bookbyid.getBooktitle());
        slb.setUserName(this.sessionUser.getFirstName());
        slb.setPrice(bookbyid.getPrice());
        this.sellBookService.sellBookSave(slb);
        return "redirect:/";
    }


    //search book by name
    @RequestMapping("/search")
    public String searchBook(@RequestParam("searchValue") String searchValue, Model model) {
        try {
            List<Book> book = (List<Book>) this.bookDao.findBybooktitleLike(searchValue);
            model.addAttribute("books", book);
            return "SearchedBooks";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SearchedBooks";
    }

    //return all users
    @RequestMapping("/users")
    public String getAllUsers(Model model) {
        List<com.library.model.User> Users = userService.getAllUsers();
        model.addAttribute("Users", Users);

        return "viewUser";
    }

    //	get user details handler
    @RequestMapping(value = "/userdetails/{id}")
    public ModelAndView getUserById(@PathVariable(name = "id") Integer uid, Model model) {
        System.out.println("inside userdetails handler   " + uid);
//		int Id=Integer.parseInt(uid);
        com.library.model.User myuser = userDao.getById(uid);

        // System.out.println(myuser.getFirstName());


        ModelAndView mv = new ModelAndView("userDetails");
        mv.addObject("user", myuser);
        return mv;
    }

    //delete user handler
    @RequestMapping(value = "/userdelete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id) {
        userService.deleteuser(id);

        return "redirect:/users";
    }

    //	update User handler
    @RequestMapping(value = "/userupdate/{id}")
    public String updateUser(@PathVariable(name = "id") Integer id, Model model) {
        com.library.model.User user = userDao.getById(id);
        model.addAttribute("user", user);
        return "register";
    }

    //get all purchased books
    @RequestMapping("/sellBooks")
    public String getAllsellBooks(Model model) {
        List<SellBook> allSellBook = this.sellBookService.getAllSellBook();

        model.addAttribute("allSellBook", allSellBook);

        return "purchaseBooks";
    }


}
