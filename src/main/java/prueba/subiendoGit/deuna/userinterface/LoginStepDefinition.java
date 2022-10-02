package prueba.subiendoGit.deuna.userinterface;

import static com.banistmo.certificacion.exceptions.autenticacion.MensajeUsuarioBloqueadoNoVisualizadoException.MENSAJE_USUARIO_BLOQUEADO_NO_ENCONTRADO;
import static com.banistmo.certificacion.exceptions.autenticacion.MenuSvpNoVisualizadoException.MENU_VALIDACION_NO_ENCONTRADO;
import static com.banistmo.certificacion.userinterface.autenticacion.LoginPage.MSG_APLICACION;
import static com.banistmo.certificacion.userinterface.autenticacion.LoginPage.TXT_USUARIO;
import static com.banistmo.certificacion.userinterface.menu.MenuPage.BTN_MIS_PRODUCTOS;
import static com.banistmo.certificacion.utils.enums.EnumMensajes.CREDENCIALES_BLOQUEADAS;
import static com.banistmo.certificacion.utils.enums.EnumMensajes.ERROR_CREDENCIALES;
import static com.banistmo.certificacion.utils.enums.EnumVariablesSesion.CLAVE_LOGIN;
import static com.banistmo.certificacion.utils.enums.EnumVariablesSesion.USUARIO_LOGIN;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

import com.banistmo.certificacion.exceptions.autenticacion.*;
import com.banistmo.certificacion.questions.ElementoEsperado;
import com.banistmo.certificacion.questions.autenticacion.ClaveVisualizada;
import com.banistmo.certificacion.task.autenticacion.iniciosesion.IngresarCrendenciales;
import com.banistmo.certificacion.task.autenticacion.iniciosesion.IniciarSesion;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import java.time.Duration;

public class LoginStepDefinition {

  @Dado("que (.*) quiere ingresar con el usuario: (.*) y la clave: (.*)")
  @Cuando("(.*) ingresa con el usuario: (.*) y la clave: (.*)")
  public void inicioSesion(String nombreActor, String usuario, String clave) {
    theActorCalled(nombreActor).attemptsTo(IniciarSesion.enSvp(usuario, clave));
  }

  @Cuando(
      "(.*) ingresa las creneciales usuario: (.*), clave: (.*) y activa la opcion de visualizar clave")
  public void ingresarCrendenciales(String nombreActor, String usuario, String clave) {
    theActorCalled(nombreActor).attemptsTo(IngresarCrendenciales.hastaClave(usuario, clave));
  }

  @Y("^el inicia sesion en la app$")
  public void elIniciaSesionEnLaApp() {
    theActorInTheSpotlight()
        .wasAbleTo(
            IniciarSesion.enSvp(
                theActorInTheSpotlight().recall(USUARIO_LOGIN.getVariableSesion()),
                theActorInTheSpotlight().recall(CLAVE_LOGIN.getVariableSesion())));
  }

  @Cuando("(.*) ingresa las creneciales usuario: (.*), clave: (.*) y se cancela")
  public void ingresarCrendencialesCancelacion(String nombreActor, String usuario, String clave) {
    theActorCalled(nombreActor)
        .attemptsTo(IngresarCrendenciales.cancelandoInicioSesion(usuario, clave));
  }

  @Entonces("el puede observar sus productos")
  public void verificarVisibilidadProductos() {
    theActorInTheSpotlight().should(seeThat(ElementoEsperado.esVisible(
            BTN_MIS_PRODUCTOS.waitingForNoMoreThan(Duration.ofSeconds(10))))
                .orComplainWith(MenuSvpNoVisualizadoException.class, MENU_VALIDACION_NO_ENCONTRADO));
  }

  @Entonces("el podra observar sus productos con sus nuevas credenciales")
  public void verificarVisibilidadProductosCambioDeContrasenia() {
    theActorInTheSpotlight().should(seeThat(ElementoEsperado.esVisible(
            BTN_MIS_PRODUCTOS.waitingForNoMoreThan(Duration.ofSeconds(10))))
                .orComplainWith(MenuSvpNoVisualizadoException.class, MENU_VALIDACION_NO_ENCONTRADO));
  }

  @Entonces("la autenticacion deberia de ser fallida por credenciales incorrectas")
  public void verificarAutenticacionCredencialesIncorrectas() {
    theActorInTheSpotlight()
        .should(
            seeThat(
                    ElementoEsperado.esVisible(
                        MSG_APLICACION
                            .of(ERROR_CREDENCIALES.getMensaje())
                            .waitingForNoMoreThan(Duration.ofSeconds(5))))
                .orComplainWith(
                    MensajeCredencialesInvalidasNoVisualizadoException.class,
                    MensajeCredencialesInvalidasNoVisualizadoException
                        .MENSAJE_CREDIALES_INCORRECTAS_NO_ENCONTRADO));
  }

  @Entonces("la autenticacion deberia de ser fallida por usuario bloqueado")
  public void verificarAutenticacionUsuarioBloqueado() {
    theActorInTheSpotlight()
        .should(
            seeThat(
                    ElementoEsperado.esVisible(
                        MSG_APLICACION.of(CREDENCIALES_BLOQUEADAS.getMensaje())))
                .orComplainWith(
                    MensajeUsuarioBloqueadoNoVisualizadoException.class,
                    MENSAJE_USUARIO_BLOQUEADO_NO_ENCONTRADO));
  }

  @Cuando("^el visualiza su clave ingresada: (.*)$")
  public void verificarClaveMostrada(String clave) {
    theActorInTheSpotlight()
        .should(
            seeThat(ClaveVisualizada.esCorrecta(clave))
                .orComplainWith(
                    ClaveNoMostradaCorrectamente.class,
                    ClaveNoMostradaCorrectamente.CLAVE_NO_MOSTRADA_CORRECTAMENTE));
  }

  @Entonces("el deberia regresar al ingreso de usuario")
  public void verificarCampoUsuarioVisible() {
    theActorInTheSpotlight()
        .should(
            seeThat(ElementoEsperado.esVisible(TXT_USUARIO))
                .orComplainWith(
                    CampoUsuarioNoVisualizado.class,
                    CampoUsuarioNoVisualizado.CAMPO_USUARIO_NO_VISUALIZADO));
  }
}
