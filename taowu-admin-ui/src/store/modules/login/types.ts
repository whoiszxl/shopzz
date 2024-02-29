export interface MemberState {
    id: number;
    username: string;
    avatar?: string;
    realName: string;
    gender: number;
    mobile?: string;
    email: string;
    googleStatus: number;
    lastLoginTime?: string;
    ip?: string;
    location?: string;
    browser?: string;
    permissions: Array<string>;
    roles: Array<string>;
}
