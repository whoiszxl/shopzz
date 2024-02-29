import { App } from 'vue';
import { use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { BarChart, LineChart, PieChart, RadarChart } from 'echarts/charts';
import {
  GridComponent,
  TooltipComponent,
  LegendComponent,
  DataZoomComponent,
  GraphicComponent,
} from 'echarts/components';
import Chart from './chart/index.vue';
import Breadcrumb from './breadcrumb/index.vue';
import download from './download';
import RangePicker from './range-picker/index.vue';
import RightToolbar from './right-toolbar/index.vue';
import SvgIcon from './svg-icon/index.vue';
import IconSelect from './icon-select/index.vue';

// Manually introduce ECharts modules to reduce packing size

use([
  CanvasRenderer,
  BarChart,
  LineChart,
  PieChart,
  RadarChart,
  GridComponent,
  TooltipComponent,
  LegendComponent,
  DataZoomComponent,
  GraphicComponent,
]);

export default {
  install(Vue: App) {
    Vue.config.globalProperties.download = download;
    Vue.component('Chart', Chart);
    Vue.component('Breadcrumb', Breadcrumb);
    Vue.component('RangePicker', RangePicker);
    Vue.component('RightToolbar', RightToolbar);
    Vue.component('SvgIcon', SvgIcon);
    Vue.component('IconSelect', IconSelect);
  },
};
