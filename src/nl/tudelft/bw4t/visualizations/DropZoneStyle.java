package nl.tudelft.bw4t.visualizations;

import java.awt.Color;

import nl.tudelft.bw4t.map.ColorTranslator;
import nl.tudelft.bw4t.zone.DropZone;
import saf.v3d.scene.Position;

public class DropZoneStyle extends BoundedMoveableObjectStyle<DropZone> {
	@Override
	public Color getColor(DropZone dropZone) {
		return dropZone.getColor();
	}

	@Override
	public String getLabel(DropZone dropZone) {
		return dropZone.getSequence().toString();
	}

	@Override
	public Position getLabelPosition(DropZone dropZone) {
		return Position.SOUTH;
	}

	@Override
	public Color getLabelColor(DropZone dropZone) {
		if (dropZone.getSequence().isEmpty()) {
			return super.getLabelColor(dropZone);
		}
		return ColorTranslator.translate2Color(dropZone.getSequence().get(0)
				.toString());
	}
}
