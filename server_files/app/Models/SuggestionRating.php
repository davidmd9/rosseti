<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SuggestionRating extends Model
{
    use HasFactory;

    protected $table = 'suggestion_ratings';
    protected $primaryKey = 'id';
    public $timestamps = false;
    protected $fillable = [
        'suggestion_id',
        'user_id',
        'value',
        'datetime'
    ];


    public function suggestion(){
        return $this->belongsTo(Suggestion::class);
    }

    public function user(){
        return $this->belongsTo(User::class);
    }


}
