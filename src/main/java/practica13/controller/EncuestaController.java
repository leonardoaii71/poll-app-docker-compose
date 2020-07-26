package practica13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practica13.Models.Encuesta;
import practica13.Models.EncuestaResponse;
import practica13.Models.Usuario;
import practica13.Services.EncuestaService;
import practica13.Services.UsuarioService;
import practica13.Services.UsuarioServiceImpl;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/encuestas")
public class EncuestaController {

    private static String UPLOADED_FOLDER = "/img/";

    @Autowired
    private EncuestaService encuestaService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/")
    public String encuestas(Model model)
    {
        List<Encuesta> encuestas = new ArrayList<>();
        encuestas = encuestaService.listarEncuestas();
        model.addAttribute("encuestas", encuestas);
        return "encuestasview";
    }

    @PostMapping("/crear/")
    public String crearEncuesta(@RequestParam("cumplieronExpectativas") int cumplieronExpectativas,
                              @RequestParam("dominioDelTema") int dominioDelTema,
                              @RequestParam("instalacionesConfortables") int instalacionesConfortables,
                              @RequestParam("comentario") String comentario,
                              RedirectAttributes redirectAttributes) {
        Encuesta encuesta = new Encuesta();
        encuesta.setComentario(comentario);
        encuesta.setCumplieronExpectativas(cumplieronExpectativas);
        encuesta.setDominioDelTema(dominioDelTema);
        encuesta.setInstalacionesConfortables(instalacionesConfortables);
        System.out.println(encuesta);
        encuestaService.crearEncuesta(encuesta);
        return "redirect:/encuestas/";
    }

    @RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
    public String ver(Model model, @PathVariable String id)
    {
        Encuesta encuesta = encuestaService.encontrarEncuestaPorId(Long.parseLong(id));
        model.addAttribute("encuesta", encuesta);
        return "verencuesta";
    }

    @GetMapping(value = "/data/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Encuesta postResponseController(@PathVariable Long id ) {
        Encuesta encuesta;
        try {
            encuesta = encuestaService.encontrarEncuestaPorId(id);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }

        return encuesta;
    }

    @PostMapping("/modificar/")
    public String modificarEncuesta(@RequestParam("id2") String id,
                                    @RequestParam("cumplieronExpectativas2") int cumplieronExpectativas,
                                    @RequestParam("dominioDelTema2") int dominioDelTema,
                                    @RequestParam("instalacionesConfortables2") int instalacionesConfortables,
                                    @RequestParam("comentario2") String comentario,
                                    RedirectAttributes redirectAttributes) {

        Encuesta encuesta = encuestaService.encontrarEncuestaPorId(Long.parseLong(id));
        encuesta.setComentario(comentario);
        encuesta.setCumplieronExpectativas(cumplieronExpectativas);
        encuesta.setDominioDelTema(dominioDelTema);
        encuesta.setInstalacionesConfortables(instalacionesConfortables);
        encuestaService.actualizarEncuesta(encuesta);
        return "redirect:/encuestas/";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String borrarEncuesta(@PathVariable String id) {
        Encuesta encuesta = encuestaService.encontrarEncuestaPorId(Long.parseLong(id));
        encuestaService.borrarEncuesta(encuesta);
        return "redirect:/encuestas/";
    }
}
