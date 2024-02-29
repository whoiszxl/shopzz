import { DirectiveBinding } from 'vue';
import {useLoginStore} from '@/store';

function checkPermission(el: HTMLElement, binding: DirectiveBinding) {
  const { value } = binding;
  const loginStore = useLoginStore();
  const { permissions, roles } = loginStore;
  const superAdmin = 'admin';
  const allPermission = '*';

  if (Array.isArray(value) && value.length > 0) {
    const permissionValues = value;

    const hasPermission = permissions.some((permission: string) => {
      return (
          allPermission === permission || permissionValues.includes(permission)
      );
    });

    const hasRole = roles.some((role: string) => {
      return superAdmin === role || permissionValues.includes(role);
    });

    if (!hasPermission && !hasRole && el.parentNode) {
      el.parentNode.removeChild(el);
    }
  } else {
    throw new Error(
        `need roles! Like v-permission="['admin','system:user:add']"`
    );
  }
}

export default {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    checkPermission(el, binding);
  },
  updated(el: HTMLElement, binding: DirectiveBinding) {
    checkPermission(el, binding);
  },
};
