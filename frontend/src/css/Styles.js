import styled from 'styled-components'

export const BoardContent = styled.div`
    width: 100%;
    padding: 0.5rem;
    height:160px;
    font-size:0.75rem;
    overflow: auto;
    &::-webkit-scrollbar {
    width: 4px;
    }
    &::-webkit-scrollbar-thumb {
    border-radius: 2px;
    background: #ccc;
    }

`;