package prueba.subiendoGit.deuna.userinterface;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

import com.banistmo.certificacion.task.autenticacion.cerrarsesion.CerrarSesion;
import cucumber.api.java.es.Cuando;

public class LogoutStepDefinition {

  @Cuando("^el cierra sesion en la aplicacion por la (.*)$")
  public void elUsuarioSaleDeLaAplicacionPorMedioDelBoton(String formaCierreSesion) {
    theActorInTheSpotlight().attemptsTo(CerrarSesion.enSvp(formaCierreSesion));
  }
}
