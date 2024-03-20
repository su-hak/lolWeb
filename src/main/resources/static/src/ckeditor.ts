/**
 * @license Copyright (c) 2014-2024, CKSource Holding sp. z o.o. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

import { ClassicEditor } from '@ckeditor/ckeditor5-editor-classic';

import { Alignment } from '@ckeditor/ckeditor5-alignment';
import { Bold, Italic, Underline } from '@ckeditor/ckeditor5-basic-styles';
import type { EditorConfig } from '@ckeditor/ckeditor5-core';
import { Essentials } from '@ckeditor/ckeditor5-essentials';
import { FontBackgroundColor, FontColor, FontFamily, FontSize } from '@ckeditor/ckeditor5-font';
import { Heading } from '@ckeditor/ckeditor5-heading';
import { Highlight } from '@ckeditor/ckeditor5-highlight';
import {
	AutoImage,
	Image,
	ImageInsert,
	ImageResize,
	ImageStyle,
	ImageToolbar,
	ImageUpload
} from '@ckeditor/ckeditor5-image';
import { MediaEmbed, MediaEmbedToolbar } from '@ckeditor/ckeditor5-media-embed';
import { Paragraph } from '@ckeditor/ckeditor5-paragraph';
import {
	Table,
	TableCellProperties,
	TableColumnResize,
	TableProperties,
	TableToolbar
} from '@ckeditor/ckeditor5-table';
import { Undo } from '@ckeditor/ckeditor5-undo';
import { Base64UploadAdapter } from '@ckeditor/ckeditor5-upload';
import { WordCount } from '@ckeditor/ckeditor5-word-count';

// You can read more about extending the build with additional plugins in the "Installing plugins" guide.
// See https://ckeditor.com/docs/ckeditor5/latest/installation/plugins/installing-plugins.html for details.

class Editor extends ClassicEditor {
	public static override builtinPlugins = [
		Alignment,
		AutoImage,
		Base64UploadAdapter,
		Bold,
		Essentials,
		FontBackgroundColor,
		FontColor,
		FontFamily,
		FontSize,
		Heading,
		Highlight,
		Image,
		ImageInsert,
		ImageResize,
		ImageStyle,
		ImageToolbar,
		ImageUpload,
		Italic,
		MediaEmbed,
		MediaEmbedToolbar,
		Paragraph,
		Table,
		TableCellProperties,
		TableColumnResize,
		TableProperties,
		TableToolbar,
		Underline,
		Undo,
		WordCount
	];

	public static override defaultConfig: EditorConfig = {
		toolbar: {
			items: [
				'undo',
				'redo',
				'|',
				'heading',
				'|',
				'fontFamily',
				'fontSize',
				'fontColor',
				'fontBackgroundColor',
				'bold',
				'underline',
				'italic',
				'highlight',
				'alignment',
				'|',
				'imageUpload',
				'imageInsert',
				'mediaEmbed',
				'insertTable'
			]
		},
		language: 'ko',
		image: {
			toolbar: [
				'imageTextAlternative',
				'imageStyle:inline',
				'imageStyle:block',
				'imageStyle:side'
			]
		},
		table: {
			contentToolbar: [
				'tableColumn',
				'tableRow',
				'mergeTableCells',
				'tableCellProperties',
				'tableProperties'
			]
		}
	};
}

export default Editor;
