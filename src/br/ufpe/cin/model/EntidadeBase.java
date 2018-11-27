package br.ufpe.cin.model;

import java.io.Serializable;

import org.springframework.data.jpa.domain.AbstractPersistable;

public abstract class EntidadeBase<XYZ extends Serializable> extends AbstractPersistable<XYZ>implements Serializable {

	private static final long serialVersionUID = -5377726703339445533L;

	@Override
	public void setId(XYZ id) {
		super.setId(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		EntidadeBase other = (EntidadeBase) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
