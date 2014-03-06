package br.com.sisnema.financeiroweb.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.sisnema.financeiroweb.model.Categoria;
import br.com.sisnema.financeiroweb.negocio.CategoriaRN;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, 
			String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				CategoriaRN categoriaRN = new CategoriaRN();
				return categoriaRN.obterPorId(new Categoria(codigo));
			} catch (Exception e) {
				throw new ConverterException("Não foi possível encontrar a categoria de código " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, 
			Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			return categoria.getCodigo().toString();
		}
		return "";
	}
}
