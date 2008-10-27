package umo;

public class GeoPortProxy implements umo.GeoPort {
  private String _endpoint = null;
  private umo.GeoPort geoPort = null;
  
  public GeoPortProxy() {
    _initGeoPortProxy();
  }
  
  public GeoPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initGeoPortProxy();
  }
  
  private void _initGeoPortProxy() {
    try {
      geoPort = (new umo.GisServiceLocator()).getGisService();
      if (geoPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)geoPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)geoPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (geoPort != null)
      ((javax.xml.rpc.Stub)geoPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public umo.GeoPort getGeoPort() {
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort;
  }
  
  public java.lang.String getCapabilities(java.lang.String service) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.getCapabilities(service);
  }
  
  public java.lang.String transaction(java.lang.String requestBody) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.transaction(requestBody);
  }
  
  public java.lang.String sendRequest(java.lang.String service, java.lang.String requestBody) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.sendRequest(service, requestBody);
  }
  
  public byte[] getMapWithOptionalParams(java.lang.String version, java.lang.String layers, java.lang.String styles, java.lang.String srs, java.lang.String bbox, java.lang.String width, java.lang.String height, java.lang.String format, java.lang.String transparent, java.lang.String bgColor, java.lang.String exceptions, java.lang.String time, java.lang.String elevation) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.getMapWithOptionalParams(version, layers, styles, srs, bbox, width, height, format, transparent, bgColor, exceptions, time, elevation);
  }
  
  public java.lang.String describeFeatureType(java.lang.String service) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.describeFeatureType(service);
  }
  
  public java.lang.String getFeature(java.lang.String service, java.lang.String[] typenameList) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.getFeature(service, typenameList);
  }
  
  public java.lang.String getCapabilitiesWithOptionalParams(java.lang.String service, java.lang.String version, java.lang.String format, java.lang.String updateSequence) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.getCapabilitiesWithOptionalParams(service, version, format, updateSequence);
  }
  
  public byte[] getMap(java.lang.String version, java.lang.String layers, java.lang.String styles, java.lang.String srs, java.lang.String bbox, java.lang.String width, java.lang.String height, java.lang.String format) throws java.rmi.RemoteException{
    if (geoPort == null)
      _initGeoPortProxy();
    return geoPort.getMap(version, layers, styles, srs, bbox, width, height, format);
  }
  
  
}