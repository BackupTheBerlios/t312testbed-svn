/**
 * GeoPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package umo;

public interface GeoPort extends java.rmi.Remote {
    public java.lang.String getCapabilities(java.lang.String service) throws java.rmi.RemoteException;
    public java.lang.String transaction(java.lang.String requestBody) throws java.rmi.RemoteException;
    public java.lang.String sendRequest(java.lang.String service, java.lang.String requestBody) throws java.rmi.RemoteException;
    public byte[] getMapWithOptionalParams(java.lang.String version, java.lang.String layers, java.lang.String styles, java.lang.String srs, java.lang.String bbox, java.lang.String width, java.lang.String height, java.lang.String format, java.lang.String transparent, java.lang.String bgColor, java.lang.String exceptions, java.lang.String time, java.lang.String elevation) throws java.rmi.RemoteException;
    public java.lang.String describeFeatureType(java.lang.String service) throws java.rmi.RemoteException;
    public java.lang.String getFeature(java.lang.String service, java.lang.String[] typenameList) throws java.rmi.RemoteException;
    public java.lang.String getCapabilitiesWithOptionalParams(java.lang.String service, java.lang.String version, java.lang.String format, java.lang.String updateSequence) throws java.rmi.RemoteException;
    public byte[] getMap(java.lang.String version, java.lang.String layers, java.lang.String styles, java.lang.String srs, java.lang.String bbox, java.lang.String width, java.lang.String height, java.lang.String format) throws java.rmi.RemoteException;
}
