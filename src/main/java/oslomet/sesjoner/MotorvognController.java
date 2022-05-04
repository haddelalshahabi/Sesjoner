package oslomet.sesjoner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class MotorvognController {

    @Autowired
    private MotorvognRepository rep;

    @Autowired
    private HttpSession session;

    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException {
        List<Bil> alleBiler = rep.hentAlleBiler();
        if(alleBiler==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
        return alleBiler;
    }

    @PostMapping("/lagre")
    public void lagreKunde(Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if(validerMotorvognOK(motorvogn)){
            if(!rep.lagreMotorvogn(motorvogn)){
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
            }
        }
        else{
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Valideringsfeil -prøv igjen senere");
        }
    }

    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlleMotorvogner(HttpServletResponse response) throws IOException {
        if ((boolean)session.getAttribute("loggetInn")) {
            List<Motorvogn> alleMotorvogner = rep.hentAlleMotorvogner();
            if (alleMotorvogner == null) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB -prøv igjen senere");
            }
            return alleMotorvogner;
        }
        else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Kan ikke vise register : Du må være logget inn");
            return null;
        }
    }

    @GetMapping("/henteEnMotorvogn")
    public Motorvogn henteEnMotorvogn(int id, HttpServletResponse response) throws IOException{
        Motorvogn enMotorvogn = rep.henteEnMotorvogn(id);
        if(enMotorvogn == null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
        return enMotorvogn;
    }

    @PostMapping("/endre")
    public void endre(Motorvogn motorvogn, HttpServletResponse response) throws IOException{

        if(validerMotorvognOK(motorvogn)){
            if(!rep.endreMotorvogn(motorvogn)){
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
            }
        }
        else{
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Valideringsfeil -prøv igjen senere");
        }
    }

    @GetMapping("/slettEnMotorvogn")
    public void slettEnMotorvogn(String personnr, HttpServletResponse response) throws IOException{
        if(!rep.slettEnMotorvogn(personnr)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }

    @GetMapping("/slettAlle")
    public void slettAlleMotorvogner(HttpServletResponse response) throws IOException{
        if(!rep.slettAlleMotorvogner()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB -prøv igjen senere");
        }
    }

    private boolean validerMotorvognOK(Motorvogn motorvogn){
        String regexPersonnr = "[0-9]{11}";
        String regexNavn = "[a-zA-ZæøåÆØÅ .\\-]{2,20}";
        String regexAdresse = "[0-9a-zA-ZæøåÆØÅ .\\-]{2,30}";
        String regexKjennetegn = "[A-Z][A-Z][0-9]{5}";
        String regexMerke = "[a-zA-ZæøåÆØÅ.\\-]{2,10}";
        String regexType = "[0-9a-zA-ZæøåÆØÅ.\\-]{2,10}";
        boolean personnrOK = motorvogn.getPersonnr().matches(regexPersonnr);
        boolean navnOK = motorvogn.getNavn().matches(regexNavn);
        boolean adresseOK = motorvogn.getAdresse().matches(regexAdresse);
        boolean kjennetegnOK = motorvogn.getKjennetegn().matches(regexKjennetegn);
        boolean merkeOK = motorvogn.getMerke().matches(regexMerke);
        boolean typeOK = motorvogn.getType().matches(regexType);
        if (personnrOK && navnOK && adresseOK && kjennetegnOK && merkeOK && typeOK ){
            return true;
        }
        logger.error("Valideringsfeil");
        return false;
    }

    //starter å implementere logginn og -ut
    @GetMapping("/loggInn")
    public boolean loggInn(String brukernavn, String passord){
        if (rep.loggInn(brukernavn, passord)){
            session.setAttribute("loggInn", true);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/loggUt")
    public void loggUt(){
        session.setAttribute("loggInn", false);
    }
}