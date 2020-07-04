package practica13.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practica13.Models.Usuario;
import practica13.Repositories.UsuarioRepository;

import java.util.List;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Usuario crearUsuario(Usuario usuario){
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }


    public String usuarioLogueado() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
    }


    public void actualizarUsuario(Usuario usuario){
        crearUsuario(usuario);
    }
    public void borrarUsuarioPorId(long id){
        usuarioRepository.deleteById(id);
    }
    public void borrarTodosLosUsuarios(){
        usuarioRepository.deleteAll();
    }
    public List<Usuario> buscarTodosUsuarios(){
        return usuarioRepository.findAll();
    }
    @Override
    public Usuario buscarPorId(long id){
        return usuarioRepository.findById(id).get();
    }
    public Usuario findByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }
    public boolean usuarioExiste(Usuario usuario){
        return findByUsername(usuario.getUsername()) != null;
    }

}

