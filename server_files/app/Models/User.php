<?php

namespace App\Models;

use Backpack\CRUD\app\Models\Traits\CrudTrait;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Passport\HasApiTokens;
use Spatie\Permission\Traits\HasRoles;

/**
 * @property string phone
 * @property string code
 */
class User extends Authenticatable
{
    use HasFactory, Notifiable, HasRoles,CrudTrait, HasApiTokens;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'full_name',
        'email',
        'phone',
        'password',
        'topic_id',
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password',
        'remember_token',
        'email_verified_at',
        'code',
        'created_at',
        'updated_at'
    ];

    protected  $appends = [
        'comments_count',
        'ratings_count',
        'accepted_proposals_count',
        'denied_proposals_count',
        'proposals_count',
    ];
    /**
     * The attributes that should be cast to native types.
     *
     * @var array
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    public function topic(){
        return $this->belongsTo(Topic::class);
    }

    public function getCommentsCountAttribute(){
        return count(SuggestionComment::query()->where(['user_id'=>$this->id])->get()->toArray());
    }

    public function getRatingsCountAttribute(){
        return count(SuggestionRating::query()->where(['user_id'=>$this->id])->get()->toArray());
    }

    public function getProposalsCountAttribute(){
        return count(Suggestion::query()->where(['author_id'=>$this->id])->get()->toArray());
    }
    public function getAcceptedProposalsCountAttribute(){
        return count(Suggestion::query()->where(['author_id'=>$this->id,'status_id'=>4])->get()->toArray());
    }
    public function getDeniedProposalsCountAttribute(){
        return count(Suggestion::query()->where(['author_id'=>$this->id,'status_id'=>5])->get()->toArray());
    }

}
