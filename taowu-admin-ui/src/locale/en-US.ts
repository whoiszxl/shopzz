import localeMessageBox from '@/components/message-box/locale/en-US';
import localeLogin from '@/views/login/locale/en-US';

import localeWorkplace from '@/views/dashboard/workplace/locale/en-US';

import localeAdmin from '@/views/system/admin/locale/en-US';
import localeMenu from '@/views/system/menu/locale/en-US';
import localeRole from '@/views/system/role/locale/en-US';

import localeSettings from './en-US/settings';

export default {
  'menu.dashboard': 'Dashboard',
  'menu.server.dashboard': 'Dashboard-Server',
  'menu.server.workplace': 'Workplace-Server',
  'menu.list': 'List',
  'menu.system': 'System',
  'menu.result': 'Result',
  'menu.profile': 'Profile',
  'menu.user': 'User Center',
  'menu.github': 'Github',
  'navbar.docs': 'Docs',
  'navbar.action.locale': 'Switch to English',
  
  ...localeSettings,
  ...localeMessageBox,
  ...localeLogin,
  ...localeWorkplace,

  ...localeAdmin,
  ...localeRole,
  ...localeMenu,
};
