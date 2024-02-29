import {useLoginStore} from "@/store";



export default function checkPermission(permissionList: Array<string>) {
    const superAdmin = 'admin';
    const allPermission = '*';
    const loginStore = useLoginStore();
    const {permissions, roles} = loginStore;

    if(!Array.isArray(permissionList) || permissionList.length <= 0) {
        throw new Error("未配置权限");
    }

    const hasPermission = permissions.some((permission:string) => {
        return (allPermission === permission || permissionList.includes(permission));
    });

    const hasRole = roles.some((role: string) => {
        return (superAdmin === role || permissionList.includes(role));
    });

    return hasPermission || hasRole;
}