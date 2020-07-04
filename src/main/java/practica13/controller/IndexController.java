package practica13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practica13.Models.Encuesta;
import practica13.Models.Rol;
import practica13.Models.Usuario;
import practica13.Services.EncuestaService;
import practica13.Services.RolServiceImpl;
import practica13.Services.UsuarioServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class IndexController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private EncuestaService encuestaService;

    @Autowired
    private RolServiceImpl rolService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, Locale locale, HttpServletRequest request)
    {

        List<Encuesta> encuestas = encuestaService.listarEncuestas();
        model.addAttribute("encuestas",encuestas);
        model.addAttribute("puerto", ""+request.getLocalPort());

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Model model) {

        if(usuarioService.buscarTodosUsuarios().size() == 0) {
            Rol adminrol = new Rol();
            adminrol.setNombreRol("ADMIN");
            adminrol.setDeleted(false);
            adminrol = rolService.crearRol(adminrol);

            Rol participanterol = new Rol();
            participanterol.setNombreRol("PARTICIPANTE");
            Rol r = rolService.crearRol(participanterol);
            System.out.println(r + "creado");

            usuarioService.crearUsuario(new Usuario("admin", "admin", "admin@gmail.com", true, adminrol, 1));
            //Creando Participante por Default
            usuarioService.crearUsuario(new Usuario("participante", "participante", "joelant97@hotmail.com", false, participanterol, 1));
            System.out.println("users created");
        }
        return "login";
    }

}
