<?php

namespace App;

class AdminPermission extends Model
{
    protected $table = "admin_permissions";

    //权限属于哪个角色
    public function roles(){
        return $this->belongsToMany(\App\AdminRole::class, 'admin_permission_role', 'permission_id','role_id')
                    ->withPivot(['permission_id','role_id']);
    }
}
