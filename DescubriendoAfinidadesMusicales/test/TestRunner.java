import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import controlador.ControladorTest;
import negocio.*;


@Suite
@SuiteDisplayName("Test Runner - Todas las Clases de Test")
@SelectClasses({
    UsuarioTest.class,
    AristaTest.class,
    UnionFindTest.class,
    IndiceDeSimilaridadTest.class,
    GrafoTest.class,
    ControladorTest.class
})
public class TestRunner {
    
}



