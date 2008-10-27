package transaction;


public class City implements Cloneable {

	private String cityId;
	private String latitude;
	private String longitude;
	private String name;
	private String adminName;
	private String countryName;
	private String status;
	private String popClass;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPopClass() {
		return popClass;
	}

	public void setPopClass(String popClass) {
		this.popClass = popClass;
	}

	@Override
	public int hashCode() {
		return getCityId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO FIXME
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getName() + "[" + getCityId() + "]";
	}

	@Override
	public City clone() throws CloneNotSupportedException {
		City clone = (City) super.clone();
		clone.cityId = this.cityId;
		clone.latitude = this.latitude;
		clone.longitude = this.longitude;
		clone.name = this.name;
		clone.adminName = this.adminName;
		clone.countryName = this.countryName;
		clone.status = this.status;
		clone.popClass = this.popClass;
		return clone;
	}
}
