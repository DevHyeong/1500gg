import React, {useState} from 'react';


const Header = ({ user, visible, index }) =>{
    const [hover, setHover] = useState(false);
    return (
        <header className="sticky shadow top-0 z-30 px-4 lg:px-0" style={{backgroundColor: "white"}}>
            <div className="container mx-auto max-w-4xl  relative flex justify-between h-14">
                <div className='absolute right-0 top-1/3'>
                    {
                        user.authenticated && 
                        (
                        <>
                        <a href='javascript:;' className='text-gray-900 text-sm'
                        onMouseOver={(e)=>{
                            setHover(true);
                        }}>{user.nickname}님</a>
                        </>
                        )
                    }
                    {
                        !user.authenticated && <a href='/login' className='text-gray-900 text-sm'>로그인</a>
                    }
                </div>
                {
                    hover && (
                    <div className='absolute right-0 top-2/3'>
                        <ul className='bg-gray text-sm text-right p-2'>
                            <li className=''><a href={"/userInfo?id=" + user.id}>내정보</a></li>
                            <li className=''><a href={"/logout?id=" + user.id}>로그아웃</a></li>
                        </ul>
                    </div>
                    )
                }

                {
                    visible &&
                    <div className="flex w-66 absolute left-1/3 mt-2">
                        <div className="w-60 relative">
                            <svg width="20" height="20" fill="currentColor" className="absolute left-3 top-1/2 -mt-2 text-slate-400 pointer-events-none group-focus-within:text-blue-500" aria-hidden="true">
                                <path fill-rule="evenodd" clip-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" />
                            </svg>
                            <input className="w-full focus:ring-2 focus:ring-blue-500 focus:outline-none appearance-none text-sm leading-6 text-slate-900 placeholder-slate-400 py-2 pl-10 ring-1 ring-slate-200 shadow-sm" 
                            type="text" name="summoner" aria-label="Filter projects" placeholder="소환사명 검색"/>
                        </div>
                        <div className="ml-1 bg-blue-500 text-white text-sm font-medium pl-2 pr-3 py-2 cursor-pointer shadow-sm hover:bg-blue-400" onClick={ e=>{
                            let name = document.querySelector("input[name=summoner]").value;
                            location.href = "/find/" + name;
                        }} >
                            검색
                        </div>

                    </div>

                }
                
                
                
                
            </div>
            <div className="flew flew-row justify-between">
                <nav className="mx-auto max-w-4xl py-2 flex border-t border-gray-200 flex flex-row items-center justify-between" aria-label="Global">
                    <div>
                        <a href="/" className={ (index == 0 ? "bg-gray-100 " : "" ) + "hover:bg-gray-50 text-gray-900 rounded-md py-2 px-3 inline-flex items-center text-sm"}>
                        전적검색
                        </a>
                        <a href="/posts" className={ (index == 1 ? "bg-gray-100 " : "" )+ "hover:bg-gray-50 text-gray-900 hover:text-gray-900 rounded-md py-2 px-3 inline-flex items-center text-sm"}>
                        타임라인
                        </a>
                    </div>
                </nav>
            </div>
        </header>
    )

}

export default Header;