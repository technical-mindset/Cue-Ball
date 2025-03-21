
package com.cueball.portal.controller;


        import com.cueball.portal.utils.Constants;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.servlet.ModelAndView;

        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class RootController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView rootCall() {
        ModelAndView mav = new ModelAndView("redirect:/login");
//        ModelAndView mav = new ModelAndView(Constants.RA_ROOT_PAGE);
        return mav;
    }



    @GetMapping ("/login")
    public ModelAndView loginCall(
            HttpServletResponse response,
            HttpServletRequest request
    ) {

        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies){
                String cookieName = cookie.getName();
                if(cookieName.equals("JSESSIONID")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);

                }
            }
        }

        ModelAndView mav = new ModelAndView(Constants.RA_ROOT_PAGE);
        return mav;
    }

}