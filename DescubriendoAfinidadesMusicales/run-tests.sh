

echo "================================================"
echo "  Descubriendo Afinidades Musicales - Tests"
echo "================================================"
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar si Maven está instalado
if command -v mvn &> /dev/null
then
    echo -e "${GREEN}✓${NC} Maven detectado"
    echo ""
    echo "Ejecutando tests con Maven..."
    echo ""
    mvn clean test
    
    echo ""
    echo "================================================"
    echo "Reportes generados en: target/surefire-reports/"
    echo "================================================"
else
    echo -e "${YELLOW}⚠${NC} Maven no encontrado"
    echo ""
    echo "Opciones:"
    echo "1. Instalar Maven: sudo apt-get install maven"
    echo "2. Ejecutar desde Eclipse IDE"
    echo "3. Ejecutar manualmente con javac y java"
    echo ""
    echo "Para ejecutar desde Eclipse:"
    echo "  - Importar el proyecto"
    echo "  - Click derecho en AllTests.java"
    echo "  - Run As → JUnit Test"
fi


