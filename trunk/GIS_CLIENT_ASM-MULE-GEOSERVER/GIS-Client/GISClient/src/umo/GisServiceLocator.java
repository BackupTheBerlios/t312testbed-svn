/**
 * GisServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umo;

import java.io.IOException;
import java.io.InputStream;

public class GisServiceLocator extends org.apache.axis.client.Service implements umo.GisService {

    public GisServiceLocator() {
        init();
    }


    public GisServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
        init();
    }

    public GisServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
        init();
    }

    // Use to get a proxy class for GisService
    private java.lang.String GisService_address = null;
  //private java.lang.String GisService_address = "http://150.254.173.202:80/services/GisService";
    
    public void init() {
        java.util.Properties props = new java.util.Properties();
        InputStream s = getClass().getClassLoader().getResourceAsStream("app.properties");
        try {
            props.load(s);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties file.", e);
        }
        String serviceurl = props.getProperty("GISService_address");
        setGisServiceEndpointAddress(serviceurl);
    }

    public java.lang.String getGisServiceAddress() {
        return GisService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GisServiceWSDDServiceName = "GisService";

    public java.lang.String getGisServiceWSDDServiceName() {
        return GisServiceWSDDServiceName;
    }

    public void setGisServiceWSDDServiceName(java.lang.String name) {
        GisServiceWSDDServiceName = name;
    }

    public umo.GeoPort getGisService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GisService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGisService(endpoint);
    }

    public umo.GeoPort getGisService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            umo.GisServiceSoapBindingStub _stub = new umo.GisServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGisServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGisServiceEndpointAddress(java.lang.String address) {
        GisService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (umo.GeoPort.class.isAssignableFrom(serviceEndpointInterface)) {
                umo.GisServiceSoapBindingStub _stub = new umo.GisServiceSoapBindingStub(new java.net.URL(GisService_address), this);
                _stub.setPortName(getGisServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GisService".equals(inputPortName)) {
            return getGisService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://umo", "GisService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://umo", "GisService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GisService".equals(portName)) {
            setGisServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
