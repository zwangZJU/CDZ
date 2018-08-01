/**
 * 
 */
package po.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class RegionDTO implements Serializable{
	
	private String region_code;
	
	/**
	 * region_name
	 */
	private String region_name;

	public String getRegion_code() {
		return region_code;
	}

	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
}
