package chat;

public class Group{
	
	Students partner;
	Students additionalPartner;
	
	public Group(Students partner, Students additionalPartner)
	{
		this.partner = partner;
		this.additionalPartner = additionalPartner;
		this.partner.setPartner(additionalPartner);
		this.additionalPartner.setPartner(partner);
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((additionalPartner == null) ? 0 : additionalPartner
						.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		Group other = (Group) obj;
		
		boolean equalSoFar = false;	
		if (partner != null){
			if (partner.equals(other.partner)) {
				equalSoFar = true;
			}
		}
		else if (other.partner == null) {
			equalSoFar = true;
		}
		
		if (equalSoFar) {
			if (additionalPartner != null) {
				if (additionalPartner.equals(other.additionalPartner)) {
					return true;
				}
			}
			else if (other.additionalPartner == null) {
				return true;
			}
		}
		
		equalSoFar = false;
		if (partner != null) {
			if (partner.equals(other.additionalPartner)) {
				equalSoFar = true;
			}
		}
		else if (other.additionalPartner == null) {
			equalSoFar = true;
		}
		
		if (equalSoFar) {
			if (additionalPartner != null) {
				if (additionalPartner.equals(other.partner)) {
					return true;
				}
			}
			else if (other.partner == null) {
				return true;
			}
		}
		
		return false;
	}
}