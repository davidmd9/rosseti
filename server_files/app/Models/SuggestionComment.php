<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SuggestionComment extends Model
{
    use HasFactory;

    protected $table = 'suggestion_comments';
    protected $primaryKey = 'id';
    public $timestamps = false;
    protected $fillable = [
        'suggestion_id',
        'user_id',
        'text',
        'datetime'
    ];

    protected $appends = ['you'];

    public function suggestion(){
        return $this->belongsTo(Suggestion::class);
    }

    public function user(){
        return $this->belongsTo(User::class);
    }

    public function getYouAttribute(){
        return $this->user_id == auth('api')->user()->id ? 1 : 0;
    }
}
