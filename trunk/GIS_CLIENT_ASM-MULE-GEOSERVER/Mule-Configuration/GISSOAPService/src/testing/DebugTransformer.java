package testing;

import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

public class DebugTransformer extends AbstractTransformer {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object doTransform(Object src, String encoding)
			throws TransformerException {
		//System.out.println(src);
		return src;
	}

}
