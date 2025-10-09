import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@Suite
@SuiteDisplayName("Descubriendo Afinidades Musicales - Suite Completa de Tests")
@SelectPackages({"negocio", "controlador"})
public class AllTests {
}



