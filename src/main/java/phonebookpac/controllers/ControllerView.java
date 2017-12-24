package phonebookpac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import phonebookpac.model.Account;
import phonebookpac.service.AccountService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by macbookair on 08.03.17.
 */
@Controller
public class ControllerView extends BaseController{


    @Autowired
    private AccountService accountService;

    @RequestMapping(value ={"/","/home"}, method = RequestMethod.GET)
    public String home(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            String login = userDetail.getUsername();
            Account account = accountService.findAccountByLogin(login);

            if (account != null){
                model.addAttribute("accountId", account.getId());
                return "home";
            }
        }
            System.out.println("Не прошел аутентификацию ");
            return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/registration")
    public String registratPage() {
        return "registration";
    }

    @RequestMapping(value = "registration/confirm", method = RequestMethod.POST)
    public String registrationConfirm(@RequestParam(name = "name", required = true) String name,
                                      @RequestParam(name = "surname", required = true) String surname,
                                      @RequestParam(name = "lastName", required = true) String lastName,
                                      @RequestParam(name = "login", required = true) String login,
                                      @RequestParam(name = "password", required = true) String password,
                                      @RequestParam(name = "confirmPass", required = true) String confirmPass,
                                      Model model){
        if (!password.equals(confirmPass)){
            model.addAttribute("confirmPass", "Confirmation password is NOT same to your password, try again.");
            return "registration";
        }
        Account account = accountService.createAccount(name, surname,lastName,login,password,confirmPass);
        if (account != null){
            return "login";
        }else return  "registration";
    }

    @RequestMapping(value = "/logout")
    public String logoutPage(Model model) {
        return "login";
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Неверный логин или пароль";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Неверный логин или пароль";
        }
        return error;
    }


}
