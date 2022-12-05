package es.mcg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.mcg.WriterXmlCar.Coche.Motor;

public class WriterXmlCar {
    public static Coche createNewCar()
    {
        return new Coche("Volkswagen", "Polo", new Motor(1500, "diesel"), 5, 200000);
    }
    private static void writerXml(Document document, OutputStream output)
    {
        try 
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource xmlAsObject = new DOMSource(document);
            StreamResult xmlAsFile = new StreamResult(output);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}-indent-amount", "2");
            transformer.transform(xmlAsObject, xmlAsFile);
        } 
        catch (TransformerException transformerException) 
        {
            transformerException.printStackTrace();
        }
    }
    public static class Coche {
        private String marca, modelo;
        private Motor motor;
        private Integer puertas, kilometros;
        public Coche(String marca, String modelo, Motor motor, Integer puertas, Integer kilometros) {
            this.marca = marca;
            this.modelo = modelo;
            this.motor = motor;
            this.puertas = puertas;
            this.kilometros = kilometros;
        }
        public String getMarca() {
            return marca;
        }
        public void setMarca(String marca) {
            this.marca = marca;
        }
        public String getModelo() {
            return modelo;
        }
        public void setModelo(String modelo) {
            this.modelo = modelo;
        }
        public Motor getMotor() {
            return motor;
        }
        public void setMotor(Motor motor) {
            this.motor = motor;
        }
        public Integer getPuertas() {
            return puertas;
        }
        public void setPuertas(Integer puertas) {
            this.puertas = puertas;
        }
        public Integer getKilometros() {
            return kilometros;
        }
        public void setKilometros(Integer kilometros) {
            this.kilometros = kilometros;
        }
        @Override
        public String toString() {
            return "Coche [marca=" + marca + ", modelo=" + modelo + ", motor=" + motor + ", puertas=" + puertas
                    + ", kilometros=" + kilometros + "]";
        }
        public static class Motor {
            private Integer revoluciones;
            private String tipo;
            public Motor(Integer revoluciones, String tipo) {
                this.revoluciones = revoluciones;
                this.tipo = tipo;
            }
            public Integer getRevoluciones() {
                return revoluciones;
            }
            public void setRevoluciones(Integer revoluciones) {
                this.revoluciones = revoluciones;
            }
            public String getTipo() {
                return tipo;
            }
            public void setTipo(String tipo) {
                this.tipo = tipo;
            }
            @Override
            public String toString() {
                return "Motor [revoluciones=" + revoluciones + ", tipo=" + tipo + "]";
            }
        }
    }
    public static void main(String[] args) {
        Coche coche = createNewCar();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        
        try 
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileOutputStream fileOutputStream = new FileOutputStream("car-out.xml");
            Document document = documentBuilder.newDocument();
            Element carsElement = document.createElement("coches");
            Element carElement = document.createElement("coche");
            Element carMarkElement = document.createElement("marca");
            carMarkElement.setTextContent(coche.getMarca());
            Element carModelElement = document.createElement("modelo");
            carModelElement.setTextContent(coche.getModelo());
            Element carDoorsElement = document.createElement("puertas");
            carDoorsElement.setTextContent(String.valueOf(coche.getPuertas()));
            Element carKilometersElement = document.createElement("kilometros");
            carKilometersElement.setTextContent(String.valueOf(coche.getKilometros()));

            carElement.appendChild(carMarkElement);
            carElement.appendChild(carModelElement);
            carElement.appendChild(carDoorsElement);
            carElement.appendChild(carKilometersElement);

            Element carEngineElement = document.createElement("motor");
            Element carEngineRevolutionElement = document.createElement("revoluciones");
            carEngineRevolutionElement.setTextContent(String.valueOf(coche.getMotor().getRevoluciones()));
            Element carEngineTypeElement = document.createElement("tipo");
            carEngineTypeElement.setTextContent(coche.getMotor().getTipo());

            carEngineElement.appendChild(carEngineRevolutionElement);
            carEngineElement.appendChild(carEngineTypeElement);

            carElement.appendChild(carEngineElement);

            carsElement.appendChild(carElement);

            document.appendChild(carsElement);

            writerXml(document, fileOutputStream);
        } 
        catch (ParserConfigurationException | IOException xmlException) 
        {
            xmlException.printStackTrace();
        }
    }
}
