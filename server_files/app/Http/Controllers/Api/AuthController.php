<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;

class AuthController extends Controller
{

    public function auth(Request $request)
    {
        $phone = $request->post('phone');
        if (is_null($user = User::query()->where(['phone' => $phone])->first())) {
            $code = $this->generateCode(4);
            $user = new User();
            $user->phone = $phone;
            $user->code = $code;
            $user->full_name = $this->generateName();
            $user->save();
            return $this->sendCode($code, $phone);
        } else {
            $code = $this->generateCode(4);
            $user->code = $code;
            $user->save();
            return $this->sendCode($code, $phone);
        }
    }

    protected function generateName(){
        $names = [
            'Christopher',
            'Ryan',
            'Ethan',
            'John',
            'Zoey',
            'Sarah',
            'Michelle',
            'Samantha',
        ];

        $surnames = [
            'Walker',
            'Thompson',
            'Anderson',
            'Johnson',
            'Tremblay',
            'Peltier',
            'Cunningham',
            'Simpson',
            'Mercado',
            'Sellers'
        ];

        return $names[mt_rand(0, sizeof($names) - 1)].' '.$surnames[mt_rand(0, sizeof($surnames) - 1)];
    }

    private function generateCode($length)
    {
        $str = '0123456789';
        return substr(str_shuffle($str), 0, $length);
    }

    private function sendCode($code, $phone)
    {
        $url = "https://sms.ru/sms/send?api_id=2083D547-53D2-D9B2-162C-F35E20955176&to=" . $phone . "&msg=" . $code . "&json=1";
        $body = file_get_contents($url);
        $json = json_decode($body);
        if ($json) {
            if ($json->status == "OK") {
                foreach ($json->sms as $phone => $data) {
                    if ($data->status == "OK") {
                        return response()->json(['success' => true, 'message' => 'Код отправлен.']);
                    } else {
                        return response()->json(['success' => false, 'error' => 'some error'], 500);
                    }
                }
            } else {
                return response()->json(['success' => false, 'error' => $json->status_text], 500);
            }
        } else {
            return response()->json(['success' => false, 'error' => 'Запрос не выполнился. Не удалось установить связь с сервером.'], 500);
        }
        return response()->json(['success' => true, 'message' => 'Code sent.']);
    }

    public function verifyCode(Request $request)
    {
        if (!is_null($user = User::query()->where(['code' => $request->post('code'), 'phone' => $request->post('phone')])->first())) {
            $token = $user->createToken('LaravelAuthApp')->accessToken;
            return response()->json(['success' => true, 'token' => $token], 200);
        }
        return response()->json(['success' => false, 'error' => 'Invalid data'], 500);

    }
}
